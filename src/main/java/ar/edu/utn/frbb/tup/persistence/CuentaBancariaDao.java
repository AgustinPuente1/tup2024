package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;

import java.util.List;

public interface CuentaBancariaDao {
    List<CuentaBancaria> getAllCuentasBancarias();
    
    List<CuentaBancaria> getCuentasBancariasByDni(long dni) throws CuentaNoExisteException;

    CuentaBancaria getCuentaBancariaById(long id) throws CuentaNoExisteException;
    
    List<Transacciones> getTransaccionesById(long id) throws CuentaNoExisteException;
    
    List<Transferencias> getTransferenciasById(long id) throws CuentaNoExisteException;

    CuentaBancaria createCuentaBancaria(CuentaBancaria cuentaBancaria);

    CuentaBancaria addDeposito(long id, float monto) throws CuentaNoExisteException;

    CuentaBancaria addRetiro(long id, float monto) throws CuentaNoExisteException;

    void deleteCuentaBancaria(long id) throws CuentaNoExisteException;
}