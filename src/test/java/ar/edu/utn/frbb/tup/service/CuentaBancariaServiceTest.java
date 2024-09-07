package ar.edu.utn.frbb.tup.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;
import ar.edu.utn.frbb.tup.controllers.dto.DepositoRetiroDto;
import ar.edu.utn.frbb.tup.model.Banco;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;
import ar.edu.utn.frbb.tup.persistence.imp.ClienteDaoImp;
import ar.edu.utn.frbb.tup.persistence.imp.CuentaBancariaDaoImp;
import ar.edu.utn.frbb.tup.service.imp.CuentaBancariaServiceImp;

@ExtendWith(MockitoExtension.class)
public class CuentaBancariaServiceTest {
    @Mock 
    private ClienteDaoImp clienteDao;
    
    @Mock
    private CuentaBancariaDaoImp cuentaBancariaDao;

    @Mock 
    private Banco banco;

    @InjectMocks
    private CuentaBancariaServiceImp cuentaBancariaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearCuenta() throws ClienteNoExisteException, CuentaAlreadyExistsException, SaldoNoValidoException{
        Cliente cliente = new Cliente("Juan", "Perez", 12345678, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
        cuentaBancariaDto.setTitular(12345678);
        cuentaBancariaDto.setMoneda("USD");
        cuentaBancariaDto.setTipoCuenta("AHORRO");
        cuentaBancariaDto.setSaldo(1000);

        when(cuentaBancariaDao.createCuentaBancaria(any(CuentaBancaria.class))).thenReturn(null);
        when(clienteDao.getCliente(anyLong())).thenReturn(cliente);

        CuentaBancaria cuenta = cuentaBancariaService.crearCuenta(cuentaBancariaDto);

        assertEquals(cuenta.getTitular(), cuentaBancariaDto.getTitular());
    }

    @Test
    public void testCrearCuentaConSaldoNegativo() throws CuentaAlreadyExistsException, SaldoNoValidoException, ClienteNoExisteException{
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
        cuentaBancariaDto.setTitular(12345678);
        cuentaBancariaDto.setMoneda("USD");
        cuentaBancariaDto.setTipoCuenta("AHORRO");
        cuentaBancariaDto.setSaldo(-1000);

        when(clienteDao.getCliente(anyLong())).thenReturn(null);

        assertThrows(SaldoNoValidoException.class, () -> cuentaBancariaService.crearCuenta(cuentaBancariaDto));
    }

    @Test 
    public void testCrearCuentaConCuentaExistente() throws CuentaAlreadyExistsException, SaldoNoValidoException, ClienteNoExisteException{
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
        cuentaBancariaDto.setTitular(12345678);
        cuentaBancariaDto.setMoneda("USD");
        cuentaBancariaDto.setTipoCuenta("AHORRO");
        cuentaBancariaDto.setSaldo(1000);
        Cliente cliente = new Cliente("Juan", "Perez", 12345678, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        CuentaBancaria cuenta = new CuentaBancaria(cuentaBancariaDto);
        cliente.addCuentas(cuenta);

        when(clienteDao.getCliente(anyLong())).thenReturn(cliente);

        assertThrows(CuentaAlreadyExistsException.class, () -> cuentaBancariaService.crearCuenta(cuentaBancariaDto));
    }

    @Test
    public void testAgregarDepositoCuentaNoeExiste() throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException{
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
        cuentaBancariaDto.setTitular(12345678);
        cuentaBancariaDto.setMoneda("USD");
        cuentaBancariaDto.setTipoCuenta("AHORRO");
        cuentaBancariaDto.setSaldo(1000);
        Cliente cliente = new Cliente("Juan", "Perez", 12345678, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        CuentaBancaria cuenta = new CuentaBancaria(cuentaBancariaDto);

        when(clienteDao.getCliente(anyLong())).thenReturn(cliente);
        when(cuentaBancariaDao.getCuentaBancariaById(anyLong())).thenReturn(cuenta);
        
        DepositoRetiroDto depositoRetiroDto = new DepositoRetiroDto();
        depositoRetiroDto.setMonto(1000);
        depositoRetiroDto.setMoneda("USD");
        assertThrows(CuentaNoExisteException.class, () -> cuentaBancariaService.agregarDeposito(0, depositoRetiroDto));
    }

    @Test
    public void testAgregarDepositoSaldoNegativo() throws CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException{
        DepositoRetiroDto depositoRetiroDto = new DepositoRetiroDto();
        depositoRetiroDto.setMonto(-1000);
        depositoRetiroDto.setMoneda("USD");
        assertThrows(SaldoNoValidoException.class, () -> cuentaBancariaService.agregarDeposito(0, depositoRetiroDto));
    }

    @Test 
    public void testAgregarRetiroLimiteSobrepasado() throws CuentaNoExisteException, ClienteNoExisteException{
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
        cuentaBancariaDto.setTitular(12345678);
        cuentaBancariaDto.setMoneda("USD");
        cuentaBancariaDto.setTipoCuenta("AHORRO");
        cuentaBancariaDto.setSaldo(1000);
        CuentaBancaria cuenta = new CuentaBancaria(cuentaBancariaDto);

        when(cuentaBancariaDao.getCuentaBancariaById(0)).thenReturn(cuenta);
        when(clienteDao.getCliente(12345678)).thenReturn(new Cliente("Juan", "Perez", 12345678, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F"));
        when(banco.getLimiteSobregiro()).thenReturn(100000f);

        DepositoRetiroDto depositoRetiroDto = new DepositoRetiroDto();
        depositoRetiroDto.setMonto(30000000);
        depositoRetiroDto.setMoneda("ARS");
        assertThrows(SaldoNoValidoException.class, () -> cuentaBancariaService.agregarRetiro(0, depositoRetiroDto));
    }
}
