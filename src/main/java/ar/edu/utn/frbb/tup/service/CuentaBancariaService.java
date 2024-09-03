package ar.edu.utn.frbb.tup.service;

import java.util.List;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;

public interface CuentaBancariaService {
    List<CuentaBancaria> obtenerAllCuentas();

    List<CuentaBancaria> obtenerCuentasPorTitular(long titular) throws CuentaNoExisteException;

    CuentaBancaria obtenerCuentaPorId(long id) throws CuentaNoExisteException;

    List<Transacciones> obtenerTransaccionesPorId(long id) throws CuentaNoExisteException;
    
    List<Transferencias> obtenerTransferenciasPorId(long id) throws CuentaNoExisteException;

    CuentaBancaria crearCuenta(CuentaBancariaDto cuentaBancariaDto) throws ClienteNoExisteException, CuentaAlreadyExistsException, SaldoNoValidoException;

    CuentaBancaria agregarDeposito(long id, float monto) throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException;

    CuentaBancaria agregarRetiro(long id, float monto) throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException;

    void borrarCuenta(long id) throws CuentaNoExisteException, ClienteNoExisteException;
}
