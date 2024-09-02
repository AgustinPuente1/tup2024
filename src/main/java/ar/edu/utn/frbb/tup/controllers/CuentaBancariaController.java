package ar.edu.utn.frbb.tup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ar.edu.utn.frbb.tup.controllers.validator.CuentaBancariaValidator;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.service.CuentaBancariaService;


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
        GET /api/cuenta/{cuentaId}/transaccion   Obtener transacciones
        POST /api/cuenta/{titular}  Crea cuenta de cliente
        PUT /api/cuenta/{titular}/{cuentaId}    Actualiza cuenta
        DELETE /api/cuenta/{titular}/{cuentaId}     Borra cuenta
     */

    @GetMapping
    public ResponseEntity <List<CuentaBancaria>> obtenerAllCuentas() {
        List<CuentaBancaria> cuentas = cuentaBancariaService.obtenerAllCuentas();
        return ResponseEntity.ok(cuentas);
    }
}

