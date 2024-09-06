package ar.edu.utn.frbb.tup.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;
import ar.edu.utn.frbb.tup.model.Banco;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaBancariaDao;
import ar.edu.utn.frbb.tup.service.CuentaBancariaService;

@Service
public class CuentaBancariaServiceImp implements CuentaBancariaService {

    private CuentaBancariaDao cuentaBancariaDao;
    private ClienteDao clienteDao;
    private Banco banco;

    @Autowired
    public CuentaBancariaServiceImp(CuentaBancariaDao cuentaBancariaDao, ClienteDao clienteDao, Banco banco) {
        this.cuentaBancariaDao = cuentaBancariaDao;
        this.clienteDao = clienteDao;
        this.banco = banco;
    }

    @Override
    public List<CuentaBancaria> obtenerAllCuentas(){
        return cuentaBancariaDao.getAllCuentasBancarias();
    }
     
    @Override
    public CuentaBancaria obtenerCuentaPorId(long idCuenta) throws CuentaNoExisteException {
        return cuentaBancariaDao.getCuentaBancariaById(idCuenta);
    }

    @Override
    public List<Transacciones> obtenerTransaccionesPorId(long idCuenta) throws CuentaNoExisteException {
        return cuentaBancariaDao.getTransaccionesById(idCuenta);
    }

    @Override
    public List<Transferencias> obtenerTransferenciasPorId(long idCuenta) throws CuentaNoExisteException {
        return cuentaBancariaDao.getTransferenciasById(idCuenta);
    }

    @Override
    public CuentaBancaria crearCuenta(CuentaBancariaDto cuentaBancariaDto) throws ClienteNoExisteException, CuentaAlreadyExistsException, SaldoNoValidoException{
        Cliente cliente = clienteDao.getCliente(cuentaBancariaDto.getTitular());

        if (cuentaBancariaDto.getSaldo() < 0){
            throw new SaldoNoValidoException("El saldo no puede ser negativo");
        } 

        CuentaBancaria cuentaBancaria = new CuentaBancaria(cuentaBancariaDto);

        for (CuentaBancaria cuenta : cliente.getCuentas()) {
            if (cuenta.getTipoCuenta().equals(cuentaBancaria.getTipoCuenta()) && cuenta.getMoneda().equals(cuentaBancaria.getMoneda())){
                throw new CuentaAlreadyExistsException("La cuenta de ese tipo y moneda ya existe");
            }
        }

        cuentaBancariaDao.createCuentaBancaria(cuentaBancaria);


        return cuentaBancaria;
    }

    @Override
    public CuentaBancaria agregarDeposito(long id, float monto, String moneda) throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException {
        if (monto < 0){
            throw new SaldoNoValidoException("El saldo no puede ser negativo");
        }
        
        CuentaBancaria cuenta = obtenerCuentaPorId(id);
        Cliente cliente = clienteDao.getCliente(cuenta.getTitular());

        for (CuentaBancaria c : cliente.getCuentas()) {
            if (c.getIdCuenta() == id){
                if (!c.getMoneda().getValue().equals(moneda)){
                    throw new MonedaNoCoincideException("La moneda no coincide, si la cuenta es en USD la moneda debe ser USD, y al reves");
                }
                cuenta = cuentaBancariaDao.addDeposito(cliente, id, monto);
                return cuenta;
            }
        }
        throw new CuentaNoExisteException("La cuenta no existe");
    }

    @Override
    public CuentaBancaria agregarRetiro(long id, float monto, String moneda) throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException{
        if (monto < 0){
            throw new SaldoNoValidoException("El saldo no puede ser negativo");
        }
        
        CuentaBancaria cuenta = obtenerCuentaPorId(id);
        Cliente cliente = clienteDao.getCliente(cuenta.getTitular());

        if ((cuenta.getSaldo() - monto) < banco.getLimiteSobregiro()){
            throw new SaldoNoValidoException("El saldo sobrepasa el limite de sobregiro");
        }

        for (CuentaBancaria c : cliente.getCuentas()) {
            if (c.getIdCuenta() == id){
                if (!c.getMoneda().getValue().equals(moneda)){
                    throw new MonedaNoCoincideException("La moneda no coincide, si la cuenta es en USD la moneda debe ser USD, y al reves");
                }
               cuenta = cuentaBancariaDao.addRetiro(cliente, id, monto);
                return cuenta;
            }
        }
        throw new CuentaNoExisteException("La cuenta no existe");
    }

    @Override
    public void borrarCuenta(long id) throws CuentaNoExisteException, ClienteNoExisteException{
        CuentaBancaria cuenta = obtenerCuentaPorId(id);
        Cliente cliente = clienteDao.getCliente(cuenta.getTitular());

        for (CuentaBancaria c : cliente.getCuentas()) {
            if (c.getIdCuenta() == id){
                cuentaBancariaDao.deleteCuentaBancaria(cliente, id);
                return;
            }
        }
        throw new CuentaNoExisteException("La cuenta no existe");
    }
}