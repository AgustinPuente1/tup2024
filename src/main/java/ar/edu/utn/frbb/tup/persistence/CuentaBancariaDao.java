package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;

import java.util.List;

public interface CuentaBancariaDao {
    List<CuentaBancaria> getAllCuentasBancarias();
    
    CuentaBancaria getCuentaBancariaById(long id) throws CuentaNoExisteException;
    
    List<Transacciones> getTransaccionesById(long id) throws CuentaNoExisteException;
    
    List<Transferencias> getTransferenciasById(long id) throws CuentaNoExisteException;

    CuentaBancaria createCuentaBancaria(CuentaBancaria cuentaBancaria) throws ClienteNoExisteException;

    CuentaBancaria addDeposito(Cliente cliente, long id, float monto) throws CuentaNoExisteException, ClienteNoExisteException;

    CuentaBancaria addRetiro(Cliente cliente, long id, float monto) throws CuentaNoExisteException, ClienteNoExisteException;

    void deleteCuentaBancaria(Cliente cliente, long id) throws CuentaNoExisteException, ClienteNoExisteException;

    void deleteCuentasPorTitular(long dni);

    void addTransferBetweenBanks(Transferencias transferencia);
    
    void addTransferInBank(Transferencias transferencia);

    void addTransaccion(Transacciones transaccion);
}