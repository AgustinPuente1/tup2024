package ar.edu.utn.frbb.tup.service;

import java.util.List;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;
import ar.edu.utn.frbb.tup.controllers.dto.DepositoRetiroDto;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;

public interface CuentaBancariaService {
    List<CuentaBancaria> obtenerAllCuentas();

    CuentaBancaria obtenerCuentaPorId(long id) throws CuentaNoExisteException;

    List<Transacciones> obtenerTransaccionesPorId(long id) throws CuentaNoExisteException;
    
    List<Transferencias> obtenerTransferenciasPorId(long id) throws CuentaNoExisteException;

    CuentaBancaria crearCuenta(CuentaBancariaDto cuentaBancariaDto) throws ClienteNoExisteException, CuentaAlreadyExistsException, SaldoNoValidoException;

    CuentaBancaria agregarDeposito(long id, DepositoRetiroDto depositoRetiroDto) throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException;

    CuentaBancaria agregarRetiro(long id, DepositoRetiroDto depositoRetiroDto) throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException;

    void borrarCuenta(long id) throws CuentaNoExisteException, ClienteNoExisteException;
}
