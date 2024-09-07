package ar.edu.utn.frbb.tup.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;
import ar.edu.utn.frbb.tup.controllers.dto.DepositoRetiroDto;
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

    /**
     * Crea una nueva cuenta bancaria para el cliente especificado.
     * Si el saldo es negativo se lanza una excepcion de SaldoNoValidoException.
     * Si la cuenta ya existe se lanza una excepcion de CuentaAlreadyExistsException.
     * @param cuentaBancariaDto la cuenta a crear
     * @return la cuenta creada
     * @throws ClienteNoExisteException si el cliente no existe
     * @throws SaldoNoValidoException si el saldo es negativo
     * @throws CuentaAlreadyExistsException si la cuenta ya existe
     */
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

    /**
     * Agrega un deposito a una cuenta existente.
     * Si el monto es negativo se lanza una excepcion de SaldoNoValidoException.
     * Si la cuenta no existe se lanza una excepcion de CuentaNoExisteException.
     * Si la moneda no coincide se lanza una excepcion de MonedaNoCoincideException.
     * @param id el id de la cuenta
     * @param depositoRetiroDto el deposito a agregar
     * @return la cuenta modificada
     * @throws CuentaNoExisteException si la cuenta no existe
     * @throws SaldoNoValidoException si el saldo es negativo
     * @throws MonedaNoCoincideException si la moneda no coincide
     */
    @Override
    public CuentaBancaria agregarDeposito(long id, DepositoRetiroDto depositoRetiroDto) throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException {
        float monto = depositoRetiroDto.getMonto();
        String moneda = depositoRetiroDto.getMoneda();
        
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

    /**
     * Agrega un retiro a una cuenta existente.
     * Si el monto es negativo se lanza una excepcion de SaldoNoValidoException.
     * Si la cuenta no existe se lanza una excepcion de CuentaNoExisteException.
     * Si la moneda no coincide se lanza una excepcion de MonedaNoCoincideException.
     * @param id el id de la cuenta
     * @param depositoRetiroDto el retiro a agregar
     * @return la cuenta modificada
     * @throws CuentaNoExisteException si la cuenta no existe
     * @throws SaldoNoValidoException si el saldo es negativo
     * @throws MonedaNoCoincideException si la moneda no coincide
     */
    @Override
    public CuentaBancaria agregarRetiro(long id, DepositoRetiroDto depositoRetiroDto) throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException{
        float monto = depositoRetiroDto.getMonto();
        String moneda = depositoRetiroDto.getMoneda();
        
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

    /**
     * Borra una cuenta bancaria.
     * Si la cuenta no existe se lanza una excepcion de CuentaNoExisteException.
     * Si el cliente no existe se lanza una excepcion de ClienteNoExisteException.
     * @param id el id de la cuenta a borrar
     * @throws CuentaNoExisteException si la cuenta no existe
     * @throws ClienteNoExisteException si el cliente no existe
     */
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
