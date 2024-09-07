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

import ar.edu.utn.frbb.tup.controllers.dto.TransferenciasDto;
import ar.edu.utn.frbb.tup.controllers.validator.TransferenciasValidator;
import ar.edu.utn.frbb.tup.model.Recibo;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentasIgualesException;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;
import ar.edu.utn.frbb.tup.service.TransferenciasService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transfer")
public class TransferenciasController {
    @Autowired 
    private TransferenciasService transferenciasService;

    @Autowired 
    private TransferenciasValidator transferenciasValidator;

    @GetMapping
    public ResponseEntity <List<Transferencias>> obtenerAllTransferencias(){
        List<Transferencias> transferencias = transferenciasService.obtenerAllTransferencias();
        return ResponseEntity.ok(transferencias);
    }

    @PostMapping
    public ResponseEntity <?> crearTransferencia(@Valid @RequestBody TransferenciasDto transferenciasDto){
        try {
            transferenciasValidator.validate(transferenciasDto);
            Recibo recibo = transferenciasService.crearTransferencia(transferenciasDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(recibo);
        } catch (DatoNoValidoException | CuentasIgualesException | MontoNoValidoException | SaldoNoValidoException | MonedaNoCoincideException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (CuentaNoExisteException | ClienteNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity <?> borrarAllTransferencias(){
        transferenciasService.borrarAllTransferencias();
        return ResponseEntity.ok("Se borraron todas las transferencias");
    }
}
