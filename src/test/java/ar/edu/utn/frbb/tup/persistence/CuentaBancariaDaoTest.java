package ar.edu.utn.frbb.tup.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.persistence.imp.ClienteDaoImp;
import ar.edu.utn.frbb.tup.persistence.imp.CuentaBancariaDaoImp;

@ExtendWith(MockitoExtension.class)
public class CuentaBancariaDaoTest {

    @InjectMocks
    private CuentaBancariaDaoImp cuentaBancariaDao;

    @Mock
    private ClienteDaoImp clienteDao;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerCuentas(){
        List<CuentaBancaria>cuentas = cuentaBancariaDao.getAllCuentasBancarias();

        assertNotNull(cuentas);
    }

    @Test
    public void testCrearDeleteYGet() throws ClienteNoExisteException, CuentaNoExisteException {
        Cliente cliente = new Cliente("Juan", "Perez", 12345678, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        CuentaBancaria cuentaBancaria = new CuentaBancaria(12345678, 200000, "CC", "USD");

        when(clienteDao.getCliente(anyLong())).thenReturn(cliente);
        when(clienteDao.updateCliente(cliente.getDni(), cliente)).thenReturn(cliente);
        cliente.addCuentas(cuentaBancaria);
        CuentaBancaria cuenta = cuentaBancariaDao.createCuentaBancaria(cuentaBancaria);
        
        
        assertNotEquals(cuenta, 0);

        assertEquals(cuenta.getIdCuenta(), cuentaBancariaDao.getCuentaBancariaById(cuenta.getIdCuenta()).getIdCuenta());
        when(clienteDao.updateCliente(cuenta.getTitular(), cliente)).thenReturn(cliente);
        cuentaBancariaDao.deleteCuentaBancaria(cliente, cuentaBancaria.getIdCuenta());

        assertThrows(CuentaNoExisteException.class, () -> cuentaBancariaDao.getCuentaBancariaById(cuentaBancaria.getIdCuenta()));
    }

    @Test
    public void testDeleteErroneo() throws CuentaNoExisteException, ClienteNoExisteException {
        CuentaBancaria cuentaBancaria = new CuentaBancaria(12345678, 200000, "CC", "USD");

        assertThrows(CuentaNoExisteException.class, () -> cuentaBancariaDao.deleteCuentaBancaria(null, cuentaBancaria.getIdCuenta()));
    }

    @Test 
    public void testObtenerTransfers() throws ClienteNoExisteException, CuentaNoExisteException{
        Cliente cliente = new Cliente("Juan", "Perez", 12345678, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        CuentaBancaria cuentaBancaria = new CuentaBancaria(12345678, 200000, "CC", "USD");

        when(clienteDao.getCliente(anyLong())).thenReturn(cliente);
        when(clienteDao.updateCliente(cliente.getDni(), cliente)).thenReturn(cliente);
        cliente.addCuentas(cuentaBancaria);
        CuentaBancaria cuenta = cuentaBancariaDao.createCuentaBancaria(cuentaBancaria);

        assertEquals(cuentaBancariaDao.getTransferenciasById(cuenta.getIdCuenta()), new ArrayList<>());
    
        cuentaBancariaDao.deleteCuentaBancaria(cliente, cuentaBancaria.getIdCuenta());

        assertThrows(CuentaNoExisteException.class, () -> cuentaBancariaDao.getCuentaBancariaById(cuentaBancaria.getIdCuenta()));
    }

    @Test 
    public void testDeposito() throws CuentaNoExisteException, ClienteNoExisteException{
        Cliente cliente = new Cliente("Juan", "Perez", 12345678, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        CuentaBancaria cuentaBancaria = new CuentaBancaria(12345678, 200000, "CC", "USD");

        when(clienteDao.getCliente(anyLong())).thenReturn(cliente);
        when(clienteDao.updateCliente(cliente.getDni(), cliente)).thenReturn(cliente);
        cliente.addCuentas(cuentaBancaria);
        CuentaBancaria cuenta = cuentaBancariaDao.createCuentaBancaria(cuentaBancaria);

        assertEquals(cuenta.getSaldo(), 200000);
        cuentaBancariaDao.addDeposito(cliente, cuenta.getIdCuenta(), 1000);

        assertEquals(cuenta.getSaldo(), 201000);

        cuentaBancariaDao.deleteCuentaBancaria(cliente, cuentaBancaria.getIdCuenta());

        assertThrows(CuentaNoExisteException.class, () -> cuentaBancariaDao.getCuentaBancariaById(cuentaBancaria.getIdCuenta()));
    }

}
