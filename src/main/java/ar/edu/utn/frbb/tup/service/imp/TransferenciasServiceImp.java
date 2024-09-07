package ar.edu.utn.frbb.tup.service.imp;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frbb.tup.controllers.dto.TransferenciasDto;
import ar.edu.utn.frbb.tup.model.Banco;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Recibo;
import ar.edu.utn.frbb.tup.model.TipoEstadoTransfers;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentasIgualesException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;
import ar.edu.utn.frbb.tup.persistence.CuentaBancariaDao;
import ar.edu.utn.frbb.tup.persistence.TransferenciasDao;
import ar.edu.utn.frbb.tup.service.TransferenciasService;

@Service
public class TransferenciasServiceImp implements TransferenciasService {

    private TransferenciasDao transferenciasDao;
    private CuentaBancariaDao cuentaBancariaDao;
    private Banco banco;

    @Autowired
    public TransferenciasServiceImp(TransferenciasDao transferenciasDao, CuentaBancariaDao cuentaBancariaDao, Banco banco) {
        this.cuentaBancariaDao = cuentaBancariaDao;
        this.transferenciasDao = transferenciasDao;
        this.banco = banco;
    }


    @Override
    public List<Transferencias> obtenerAllTransferencias() {
        return transferenciasDao.getAllTransfers();
    }

    @Override
    public Recibo crearTransferencia(TransferenciasDto transferenciasDto) throws CuentasIgualesException, MontoNoValidoException, CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException {
        //Excepciones iniciales 
        if (transferenciasDto.getCuentaOrigen() == transferenciasDto.getCuentaDestino()) {
            throw new CuentasIgualesException("La cuenta de origen no puede ser igual a la cuenta de destino");
        } else if (transferenciasDto.getMonto() <= 0) {
            throw new MontoNoValidoException("El monto no puede ser negativo o 0");
        }
        
        Recibo recibo = new Recibo();
        boolean tranferEntreBancos;
        CuentaBancaria cuentaOrigen = cuentaBancariaDao.getCuentaBancariaById(transferenciasDto.getCuentaOrigen());
        
        //Caso de saldo insuficiente 
        if (cuentaOrigen.getSaldo() - transferenciasDto.getMonto() < banco.getLimiteSobregiro()) {
            recibo.setEstado(TipoEstadoTransfers.FALLIDA);
            recibo.setMensaje(banco.getSaldoInsuficiente());
            return recibo;
        }

        CuentaBancaria cuentaDestino = new CuentaBancaria();
        //Comprobar como será la transferencia
        try {
            cuentaDestino = cuentaBancariaDao.getCuentaBancariaById(transferenciasDto.getCuentaDestino());
            tranferEntreBancos = false;
        } catch(CuentaNoExisteException e) {
            tranferEntreBancos = true;
        }

        //Caso de transferencia erronea aleatoriamente
        if (!exitoTransferencia(tranferEntreBancos)){
            recibo.setEstado(TipoEstadoTransfers.ERROR);
            recibo.setMensaje(banco.getMensajeError());
            return recibo;
        }

        //Caso de transferencia exitosa
        Transferencias transferencia = sacarComision(new Transferencias(transferenciasDto));
        if (!tranferEntreBancos){
            if (cuentaOrigen.getMoneda() != cuentaDestino.getMoneda() 
                    || transferencia.getMoneda() != cuentaOrigen.getMoneda()
                    || transferencia.getMoneda() != cuentaDestino.getMoneda()) {
                throw new MonedaNoCoincideException("La moneda no coincide");
            }
        }

        if (tranferEntreBancos) {
            transferenciasDao.transferBetweenBanks(transferencia);
            recibo.setEstado(TipoEstadoTransfers.EXITOSA);
            recibo.setMensaje(banco.getExitoEntreBancos());
            return recibo;
        } else {
            transferenciasDao.transferInBank(transferencia);
            recibo.setEstado(TipoEstadoTransfers.EXITOSA);
            recibo.setMensaje(banco.getExitoEnBanco());
            return recibo;
        }
    }

    public Transferencias sacarComision(Transferencias transferencia) {
        if (transferencia.getMoneda() == TipoMoneda.PESOS) {
            //COMISION PESOS
            if (transferencia.getMonto() > banco.getLimiteComisionArs()){
                float comision = transferencia.getMonto() * banco.getComisionTransferArs();
                transferencia.setMonto(transferencia.getMonto() - comision);
                transferencia.setComision(comision);
                return transferencia;
            } else {
                return transferencia;
            }
        } else {
            //COMISION DOLARES
            if (transferencia.getMonto() > banco.getLimiteComisionUsd()){
                float comision = transferencia.getMonto() * banco.getComisionTransferUsd();
                transferencia.setMonto(transferencia.getMonto() - comision);
                transferencia.setComision(comision);
                return transferencia;
            } else {
                return transferencia;
            }
        }
    }

    public boolean exitoTransferencia(boolean entreBancos){
        Random random = new Random();
        //TENES UN 5% DE CHANCE DE FALLAR si la transf es entre bancos
        //TENES UN 1% DE CHANCE DE FALLAR si la transf es entre cuentas del mismo banco
        if (entreBancos){
            if (random.nextInt(100) < 5){
                return false;
            } else {
                return true;
            }
        } else {
            if (random.nextInt(100) < 1){
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void borrarAllTransferencias(){
        transferenciasDao.deleteTransfers();
    }
}
