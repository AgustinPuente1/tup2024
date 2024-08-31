package ar.edu.utn.frbb.tup.controllers;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frbb.tup.controllers.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controllers.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.EdadNoValidaException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteValidator clienteValidator;

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerAllClientes() {
        List<Cliente> clientes = clienteService.obtenerAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable("dni") long dni) {
        try {
            Cliente cliente = clienteService.obtenerCliente(dni);
            return ResponseEntity.ok(cliente);
        } catch (ClienteNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody ClienteDto clienteDto) {
        try {
            clienteValidator.validate(clienteDto); // Validar el ClienteDto
            Cliente cliente = clienteService.crearCliente(clienteDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch (DatoNoValidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ClienteAlreadyExistsException | EdadNoValidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{dni}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable("dni") long dni, @RequestBody ClienteDto clienteDto) {
        try {
            clienteValidator.validate(clienteDto); // Validar el ClienteDto
            Cliente cliente = clienteService.actualizarCliente(clienteDto);
            return ResponseEntity.ok(cliente);
        } catch (DatoNoValidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ClienteNoExisteException | EdadNoValidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> borrarCliente(@PathVariable("dni") long dni) {
        try {
            clienteService.borrarCliente(dni);
            return ResponseEntity.noContent().build();
        } catch (ClienteNoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
