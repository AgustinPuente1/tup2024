package ar.edu.utn.frbb.tup.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frbb.tup.controllers.dto.TransaccionesDto;
import ar.edu.utn.frbb.tup.controllers.validator.TransaccionesValidator;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.service.TransaccionesService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaccion")
public class TransaccionesController {
    @Autowired
    private TransaccionesService transaccionesService;

    @Autowired 
    private TransaccionesValidator transaccionesValidator;

    @GetMapping
    public ResponseEntity <List<Transacciones>> obtenerAllTransacciones() {
        List<Transacciones> transacciones = transaccionesService.obtenerAllTransacciones();
        return ResponseEntity.ok(transacciones);
    }

    @PostMapping
    public ResponseEntity <?> crearTransaccion(@Valid @RequestBody TransaccionesDto transaccionesDto) {
        try {
            transaccionesValidator.validate(transaccionesDto);
            Transacciones transaccion = transaccionesService.crearTransaccion(transaccionesDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccion);
        } catch (MontoNoValidoException | MonedaNoCoincideException | DatoNoValidoException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ClienteNoExisteException | CuentaNoExisteException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity <?> borrarAllTransacciones() {
        transaccionesService.borrarAllTransacciones();
        return ResponseEntity.ok("Se borraron todas las transacciones");
    }
}
