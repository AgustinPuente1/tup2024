package ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.controllers.dto.TransaccionesDto;
import ar.edu.utn.frbb.tup.model.Banco;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.persistence.imp.CuentaBancariaDaoImp;
import ar.edu.utn.frbb.tup.persistence.imp.TransaccionesDaoImp;
import ar.edu.utn.frbb.tup.service.imp.TransaccionesServiceImp;

@ExtendWith(MockitoExtension.class)
public class TransaccionesServiceTest {
    @Mock
    private CuentaBancariaDaoImp cuentaBancariaDao;

    @Mock 
    private TransaccionesDaoImp transaccionesDao;

    @Mock
    private Banco banco;

    @InjectMocks
    private TransaccionesServiceImp transaccionesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransaccionErroresIniciales(){
        TransaccionesDto transaccionesDto = new TransaccionesDto();
        transaccionesDto.setMonto(0);

        assertThrows(MontoNoValidoException.class, () -> transaccionesService.crearTransaccion(transaccionesDto));
    }

    @Test
    public void testTransasccionEnDolaresError() throws CuentaNoExisteException{
        TransaccionesDto transaccionesDto = new TransaccionesDto();
        transaccionesDto.setMonto(10000);
        transaccionesDto.setTipo("CREDITO");
        transaccionesDto.setDescripcion("Prueba de transacción");
        transaccionesDto.setIdCuenta(1000333);

        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.setMoneda(TipoMoneda.DOLARES);

        when(cuentaBancariaDao.getCuentaBancariaById(anyLong())).thenReturn(cuenta);

        assertThrows(MonedaNoCoincideException.class, () -> transaccionesService.crearTransaccion(transaccionesDto));
    }

    @Test
    public void testTransaccionMontoNoValido() throws CuentaNoExisteException{
        TransaccionesDto transaccionesDto = new TransaccionesDto();
        transaccionesDto.setMonto(5666777);
        transaccionesDto.setTipo("CREDITO");
        transaccionesDto.setDescripcion("Prueba de transacción");
        transaccionesDto.setIdCuenta(1000333);

        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setSaldo(10000);

        when(cuentaBancariaDao.getCuentaBancariaById(anyLong())).thenReturn(cuenta);
        when(banco.getLimiteSobregiro()).thenReturn(-100000f);

        assertThrows(MontoNoValidoException.class, () -> transaccionesService.crearTransaccion(transaccionesDto));
    }

    @Test
    public void testTransaccionExitosa() throws CuentaNoExisteException, ClienteNoExisteException, MontoNoValidoException, MonedaNoCoincideException{
        TransaccionesDto transaccionesDto = new TransaccionesDto();
        transaccionesDto.setMonto(10000);
        transaccionesDto.setTipo("CREDITO");
        transaccionesDto.setDescripcion("Prueba de transacción");
        transaccionesDto.setIdCuenta(1000333);

        Transacciones transaccion = new Transacciones(transaccionesDto);

        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setSaldo(100000);

        when(cuentaBancariaDao.getCuentaBancariaById(anyLong())).thenReturn(cuenta);
        when(banco.getLimiteSobregiro()).thenReturn(-100000f);
        when(transaccionesDao.createTransaccion(any(Transacciones.class))).thenReturn(transaccion);

        transaccionesService.crearTransaccion(transaccionesDto);

        verify(transaccionesDao, times(1)).createTransaccion(any(Transacciones.class));
    }
}
