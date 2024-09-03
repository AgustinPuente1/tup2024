package ar.edu.utn.frbb.tup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;
import ar.edu.utn.frbb.tup.controllers.dto.DepositoRetiroDto;
import ar.edu.utn.frbb.tup.controllers.validator.CuentaBancariaValidator;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;
import ar.edu.utn.frbb.tup.service.CuentaBancariaService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/cuenta")
public class CuentaBancariaController {
    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @Autowired
    private CuentaBancariaValidator cuentaBancariaValidator;

    /*
     Cuentas 
        GET /api/cuenta    Obtener todas las cuentas
        GET /api/cuenta/{titular}  Obtener cuentas de cliente
        GET /api/cuenta/{cuentaId}/transfer   Obtener transfers
        GET /api/cuenta/{cuentaId}/transacciones   Obtener transacciones
        POST /api/cuenta  Crea cuenta de cliente
        POST /api/cuenta/{cuentaId}/deposito  crea un deposito
        POST /api/cuenta/{cuentaId}/retiro   crea un retiro
        DELETE /api/cuenta/{cuentaId}     Borra cuenta
     */

    @GetMapping
    public ResponseEntity <List<CuentaBancaria>> obtenerAllCuentas() {
        List<CuentaBancaria> cuentas = cuentaBancariaService.obtenerAllCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{titular}")
    public ResponseEntity <?> obtenerCuentasPorTitular(@PathVariable("titular") long titular) {
        try {
            List<CuentaBancaria> cuentas = cuentaBancariaService.obtenerCuentasPorTitular(titular);
            return ResponseEntity.ok(cuentas);
        } catch (CuentaNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{cuentaId}")
    public ResponseEntity <?> obtenerCuentaPorId(@PathVariable("cuentaId") long cuentaId) {
        try {
            CuentaBancaria cuenta = cuentaBancariaService.obtenerCuentaPorId(cuentaId);
            return ResponseEntity.ok(cuenta);
        } catch (CuentaNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{cuentaId}/transacciones")
    public ResponseEntity <?> obtenerTransacciones(@PathVariable("cuentaId") long cuentaId) {
        try {
            List<Transacciones> transacciones = cuentaBancariaService.obtenerTransaccionesPorId(cuentaId);
            return ResponseEntity.ok(transacciones);
        } catch (CuentaNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{cuentaId}/transfers")
    public ResponseEntity <?> obtenerTransfers(@PathVariable("cuentaId") long cuentaId) {
        try {
            List<Transferencias> transfers = cuentaBancariaService.obtenerTransferenciasPorId(cuentaId);
            return ResponseEntity.ok(transfers);
        } catch (CuentaNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity <?> crearCuenta(@Valid @RequestBody CuentaBancariaDto cuentaBancariaDto) {
        try {
            cuentaBancariaValidator.validate(cuentaBancariaDto);
            CuentaBancaria cuenta = cuentaBancariaService.crearCuenta(cuentaBancariaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
        } catch (DatoNoValidoException | SaldoNoValidoException | CuentaAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ClienteNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } 
    }

    @PostMapping("/{cuentaId}/deposito")
    public ResponseEntity <?> crearDeposito(@PathVariable("cuentaId") long cuentaId, @Valid @RequestBody DepositoRetiroDto depositoRetiroDto) {
        try {
            CuentaBancaria cuenta = cuentaBancariaService.agregarDeposito(cuentaId, depositoRetiroDto.getMonto());
            return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
        } catch (CuentaNoExisteException | ClienteNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SaldoNoValidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{cuentaId}/retiro")
    public ResponseEntity <?> crearRetiro(@PathVariable("cuentaId") long cuentaId, @Valid @RequestBody DepositoRetiroDto depositoRetiroDto) {
        try {
            CuentaBancaria cuenta = cuentaBancariaService.agregarRetiro(cuentaId, depositoRetiroDto.getMonto());
            return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
        } catch (CuentaNoExisteException | ClienteNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SaldoNoValidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cuentaId}")
    public ResponseEntity <?> borrarCuenta(@PathVariable("cuentaId") long cuentaId) {
        try {
            cuentaBancariaService.borrarCuenta(cuentaId);
            return ResponseEntity.ok("La cuenta se borro con exito");
        } catch (CuentaNoExisteException | ClienteNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

