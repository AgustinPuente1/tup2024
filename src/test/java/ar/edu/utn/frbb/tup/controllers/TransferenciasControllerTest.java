package ar.edu.utn.frbb.tup.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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

import ar.edu.utn.frbb.tup.controllers.dto.TransferenciasDto;
import ar.edu.utn.frbb.tup.controllers.validator.TransferenciasValidator;
import ar.edu.utn.frbb.tup.model.Recibo;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentasIgualesException;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;
import ar.edu.utn.frbb.tup.service.imp.TransferenciasServiceImp;

@ExtendWith(MockitoExtension.class)
public class TransferenciasControllerTest {
    
    @Mock
    private TransferenciasValidator transferenciasValidator;

    @Mock
    private TransferenciasServiceImp transferenciasService;

    @InjectMocks
    private TransferenciasController transferenciasController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearTransferencia() throws DatoNoValidoException, CuentasIgualesException, MontoNoValidoException, CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException {
        TransferenciasDto transferenciasDto = new TransferenciasDto(); 
        Recibo recibo = new Recibo();

        doNothing().when(transferenciasValidator).validate(transferenciasDto);
        when(transferenciasService.crearTransferencia(transferenciasDto)).thenReturn(recibo);

        ResponseEntity <?> response = transferenciasController.crearTransferencia(transferenciasDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(recibo, response.getBody());
    }
}
