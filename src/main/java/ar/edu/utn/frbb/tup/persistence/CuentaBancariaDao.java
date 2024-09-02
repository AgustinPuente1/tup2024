package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;

import java.util.List;

public interface CuentaBancariaDao {
    List<CuentaBancaria> getAllCuentasBancarias();
    
    List<CuentaBancaria> getCuentasBancariasByDni(long dni);

    CuentaBancaria getCuentaBancariaById(long id);
    
    List<Transacciones> getTransaccionesById(long id);
    
    List<Transferencias> getTransferenciasById(long id);

    CuentaBancaria createCuentaBancaria(CuentaBancaria cuentaBancaria);

    CuentaBancaria updateCuentaBancaria(Long id, CuentaBancaria cuentaBancaria);

    void deleteCuentaBancaria(Long id);
}