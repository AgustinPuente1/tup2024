package ar.edu.utn.frbb.tup.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frbb.tup.controllers.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controllers.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.EdadNoValidaException;
import ar.edu.utn.frbb.tup.service.imp.ClienteServiceImp;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock 
    private ClienteServiceImp clienteService;

    @Mock
    private ClienteValidator clienteValidator;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerCliente() throws ClienteNoExisteException{
        Cliente expected = new Cliente();
        long dni = 12345678;

        when(clienteService.obtenerCliente(12345678)).thenReturn(expected);
    
        ResponseEntity <?> response = clienteController.obtenerCliente(dni);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void testObtenerClienteFallido() throws ClienteNoExisteException {
        long dni = 12345678;

        when(clienteService.obtenerCliente(12345678)).thenThrow(ClienteNoExisteException.class);
    
        ResponseEntity <?> response = clienteController.obtenerCliente(dni);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCrearMalDto() throws DatoNoValidoException{
        ClienteDto clienteDto = new ClienteDto();
        
        doThrow(DatoNoValidoException.class).when(clienteValidator).validate(clienteDto);
        
        ResponseEntity <?> response = clienteController.crearCliente(clienteDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCrearExito() throws ClienteAlreadyExistsException, EdadNoValidaException, DatoNoValidoException{
        ClienteDto clienteDto = new ClienteDto();
        Cliente expected = new Cliente();

        doNothing().when(clienteValidator).validate(clienteDto);
        when(clienteService.crearCliente(clienteDto)).thenReturn(expected);

        ResponseEntity <?> response = clienteController.crearCliente(clienteDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void testActualizarNoValid() throws DatoNoValidoException, ClienteAlreadyExistsException, EdadNoValidaException, ClienteNoExisteException{
        ClienteDto clienteDto = new ClienteDto();
        long dni = 12345678;

        doNothing().when(clienteValidator).validate(clienteDto);
        when(clienteService.actualizarCliente(dni, clienteDto)).thenThrow(EdadNoValidaException.class);
        
        ResponseEntity <?> response = clienteController.actualizarCliente(dni, clienteDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testActualizarNoCliente() throws DatoNoValidoException, ClienteAlreadyExistsException, EdadNoValidaException, ClienteNoExisteException{
        ClienteDto clienteDto = new ClienteDto();
        long dni = 12345678;

        doNothing().when(clienteValidator).validate(clienteDto);
        when(clienteService.actualizarCliente(dni, clienteDto)).thenThrow(ClienteNoExisteException.class);
        
        ResponseEntity <?> response = clienteController.actualizarCliente(dni, clienteDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testActualizarCorrectamente() throws DatoNoValidoException, ClienteNoExisteException, EdadNoValidaException{
        ClienteDto clienteDto = new ClienteDto();
        Cliente expected = new Cliente();
        long dni = 12345678;

        doNothing().when(clienteValidator).validate(clienteDto);
        when(clienteService.actualizarCliente(dni, clienteDto)).thenReturn(expected);
        
        ResponseEntity <?> response = clienteController.actualizarCliente(dni, clienteDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test 
    public void testBorrarNoExiste() throws ClienteNoExisteException, CuentaNoExisteException{
        long dni = 12345678;

        doThrow(ClienteNoExisteException.class).when(clienteService).borrarCliente(dni);
        
        ResponseEntity <?> response = clienteController.borrarCliente(dni);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
