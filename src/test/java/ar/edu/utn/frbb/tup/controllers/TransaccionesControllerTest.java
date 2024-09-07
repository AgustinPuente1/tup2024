package ar.edu.utn.frbb.tup.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frbb.tup.controllers.dto.TransaccionesDto;
import ar.edu.utn.frbb.tup.controllers.validator.TransaccionesValidator;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.service.imp.TransaccionesServiceImp;

public class TransaccionesControllerTest {
    
    @Mock 
    private TransaccionesServiceImp transaccionesService;

    @Mock 
    private TransaccionesValidator transaccionesValidator;

    @InjectMocks
    private TransaccionesController transaccionesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearTransaccion() throws DatoNoValidoException, MontoNoValidoException, CuentaNoExisteException, MonedaNoCoincideException, ClienteNoExisteException {
        TransaccionesDto transaccionesDto = new TransaccionesDto();
        Transacciones transaccion = new Transacciones();

        doNothing().when(transaccionesValidator).validate(transaccionesDto);
        when(transaccionesService.crearTransaccion(transaccionesDto)).thenReturn(transaccion);

        ResponseEntity <?> response = transaccionesController.crearTransaccion(transaccionesDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaccion, response.getBody());
    }
}
