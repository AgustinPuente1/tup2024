package ar.edu.utn.frbb.tup.service;

import java.util.List;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;

public interface CuentaBancariaService {
    List<CuentaBancaria> obtenerAllCuentas();

    List<CuentaBancaria> obtenerCuentasPorTitular(Long titular);

    CuentaBancaria obtenerCuentaPorId(Long id);

    List<Transacciones> obtenerTransaccionesPorCuenta(Long id);
    
    List<Transferencias> obtenerTransferenciasPorCuenta(Long id);

    CuentaBancaria crearCuenta(CuentaBancariaDto cuentaBancariaDto);

    CuentaBancaria actualizarCuenta(Long id, CuentaBancariaDto cuentaBancariaDto);

    void borrarCuenta(Long id);
}
