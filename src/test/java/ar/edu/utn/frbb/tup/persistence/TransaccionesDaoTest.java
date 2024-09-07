package ar.edu.utn.frbb.tup.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.TipoTransaccion;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.persistence.imp.ClienteDaoImp;
import ar.edu.utn.frbb.tup.persistence.imp.CuentaBancariaDaoImp;
import ar.edu.utn.frbb.tup.persistence.imp.TransaccionesDaoImp;

@ExtendWith(MockitoExtension.class)
public class TransaccionesDaoTest {
    @Mock
    private ClienteDaoImp clienteDao;

    @Mock 
    private CuentaBancariaDaoImp cuentaBancariaDao;

    @InjectMocks
    private TransaccionesDaoImp transaccionesDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test 
    public void testObtenerTransacciones(){
        List<Transacciones> transacciones = new ArrayList<>();
        transaccionesDao.deleteTransacciones();
        assertEquals(transaccionesDao.getAllTransacciones(), transacciones);
    }

    @Test
    public void testCrearTransacciones() throws ClienteNoExisteException{
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setIdCuenta(12345678);
        cuentaBancaria.setSaldo(200000);
        cliente.addCuentas(cuentaBancaria);

        Transacciones transaccion = new Transacciones();
        transaccion.setIdCuenta(12345678);
        transaccion.setMonto(10000);
        transaccion.setTipo(TipoTransaccion.CREDITO);
        transaccion.setDescripcion("Prueba de transacción");

        when(clienteDao.getClienteByCuentaId(12345678)).thenReturn(cliente);
        when(clienteDao.updateCliente(cliente.getDni(), cliente)).thenReturn(cliente);
        doNothing().when(cuentaBancariaDao).addTransaccion(any(Transacciones.class));    

        transaccionesDao.createTransaccion(transaccion);

        verify(cuentaBancariaDao, times(1)).addTransaccion(any(Transacciones.class));
        transaccionesDao.deleteTransacciones();
    }
}
