package ar.edu.utn.frbb.tup.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;
import ar.edu.utn.frbb.tup.service.imp.CuentaBancariaServiceImp;

@ExtendWith(MockitoExtension.class)
public class CuentaBancariaControllerTest {
    
    @Mock
    CuentaBancariaServiceImp cuentaBancariaService;

    @Mock 
    CuentaBancariaValidator cuentaBancariaValidator;

    @InjectMocks
    CuentaBancariaController cuentaBancariaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerCuentaXId() throws CuentaNoExisteException {
        long id = 1222333;
        CuentaBancaria cuentaExpected = new CuentaBancaria();

        when(cuentaBancariaService.obtenerCuentaPorId(id)).thenReturn(cuentaExpected);

        ResponseEntity <?> response = cuentaBancariaController.obtenerCuentaPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cuentaExpected, response.getBody());
    }

    @Test 
    public void testObtenerTransacciones() throws CuentaNoExisteException{
        long id = 1222333;
        List<Transacciones> transacciones = new ArrayList<>();

        when(cuentaBancariaService.obtenerTransaccionesPorId(id)).thenReturn(transacciones);

        ResponseEntity <?> response = cuentaBancariaController.obtenerTransacciones(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacciones, response.getBody());
    }

    @Test
    public void testObtenerTransfers() throws CuentaNoExisteException{
        long id = 1222333;
        List<Transferencias> transfers = new ArrayList<>();

        when(cuentaBancariaService.obtenerTransferenciasPorId(id)).thenReturn(transfers);

        ResponseEntity <?> response = cuentaBancariaController.obtenerTransfers(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transfers, response.getBody());
    }

    @Test 
    public void testCrearNoExisteCliente() throws DatoNoValidoException, ClienteNoExisteException, CuentaAlreadyExistsException, SaldoNoValidoException{
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();

        doNothing().when(cuentaBancariaValidator).validate(cuentaBancariaDto);
        when(cuentaBancariaService.crearCuenta(cuentaBancariaDto)).thenThrow(ClienteNoExisteException.class);
        
        ResponseEntity <?> response = cuentaBancariaController.crearCuenta(cuentaBancariaDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCrearCuentaConCuentaExistente() throws CuentaAlreadyExistsException, SaldoNoValidoException, ClienteNoExisteException, DatoNoValidoException{
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();

        doNothing().when(cuentaBancariaValidator).validate(cuentaBancariaDto);
        when(cuentaBancariaService.crearCuenta(cuentaBancariaDto)).thenThrow(CuentaAlreadyExistsException.class);
        
        ResponseEntity <?> response = cuentaBancariaController.crearCuenta(cuentaBancariaDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCrearCuentaExitoso() throws DatoNoValidoException, ClienteNoExisteException, CuentaAlreadyExistsException, SaldoNoValidoException{
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
        CuentaBancaria cuentaExpected = new CuentaBancaria();

        doNothing().when(cuentaBancariaValidator).validate(cuentaBancariaDto);
        when(cuentaBancariaService.crearCuenta(cuentaBancariaDto)).thenReturn(cuentaExpected);
        
        ResponseEntity <?> response = cuentaBancariaController.crearCuenta(cuentaBancariaDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cuentaExpected, response.getBody());
    }

    @Test 
    public void testCrearDeposito() throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException{
        long id = 1222333;
        CuentaBancaria cuentaExpected = new CuentaBancaria();
        DepositoRetiroDto depositoRetiroDto = new DepositoRetiroDto();

        when(cuentaBancariaService.agregarDeposito(id, depositoRetiroDto)).thenReturn(cuentaExpected);

        ResponseEntity <?> response = cuentaBancariaController.crearDeposito(id, depositoRetiroDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cuentaExpected, response.getBody());
    }

    @Test 
    public void testCrearRetiro() throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException, MonedaNoCoincideException{
        long id = 1222333;
        CuentaBancaria cuentaExpected = new CuentaBancaria();
        DepositoRetiroDto depositoRetiroDto = new DepositoRetiroDto();

        when(cuentaBancariaService.agregarRetiro(id, depositoRetiroDto)).thenReturn(cuentaExpected);

        ResponseEntity <?> response = cuentaBancariaController.crearRetiro(id, depositoRetiroDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cuentaExpected, response.getBody());
    }

    @Test 
    public void testBorrarCuenta() throws CuentaNoExisteException, ClienteNoExisteException{
        long id = 1222333;

        doNothing().when(cuentaBancariaService).borrarCuenta(id);

        ResponseEntity <?> response = cuentaBancariaController.borrarCuenta(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
