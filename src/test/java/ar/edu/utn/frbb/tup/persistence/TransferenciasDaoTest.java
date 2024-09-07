package ar.edu.utn.frbb.tup.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.persistence.imp.ClienteDaoImp;
import ar.edu.utn.frbb.tup.persistence.imp.CuentaBancariaDaoImp;
import ar.edu.utn.frbb.tup.persistence.imp.TransferenciasDaoImp;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.TipoMoneda;

@ExtendWith(MockitoExtension.class)
public class TransferenciasDaoTest {
    @Mock
    private ClienteDaoImp clienteDao;

    @Mock 
    private CuentaBancariaDaoImp cuentaBancariaDao;

    @InjectMocks
    private TransferenciasDaoImp transferenciasDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTransfers() {
        List<Transferencias> transfers = new ArrayList<>();
        transferenciasDao.deleteTransfers();
        assertEquals(transferenciasDao.getAllTransfers(), transfers);
    }

    @Test
    public void testTransferEntreBancos() throws ClienteNoExisteException {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setIdCuenta(12345678);
        cuentaBancaria.setSaldo(200000);
        cliente.addCuentas(cuentaBancaria);

        Transferencias transferencia = new Transferencias();
        transferencia.setCuentaOrigen(12345678);
        transferencia.setCuentaDestino(87654321);
        transferencia.setMonto(50000);
        transferencia.setMoneda(TipoMoneda.DOLARES);

        when(clienteDao.getClienteByCuentaId(12345678)).thenReturn(cliente);
        when(clienteDao.updateCliente(eq(12345678L), any(Cliente.class))).thenReturn(cliente);
        doNothing().when(cuentaBancariaDao).addTransferBetweenBanks(any(Transferencias.class));

        transferenciasDao.transferBetweenBanks(transferencia);

        transferenciasDao.deleteTransfers();
    }

    @Test 
    public void testTransferEnBanco() throws ClienteNoExisteException{
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        CuentaBancaria cuenta1 = new CuentaBancaria();
        cuenta1.setIdCuenta(12345678);
        cuenta1.setSaldo(200000);
        cliente.addCuentas(cuenta1);

        CuentaBancaria cuenta2 = new CuentaBancaria();
        cuenta2.setIdCuenta(87654321);
        cuenta2.setSaldo(200000);
        cliente.addCuentas(cuenta2);

        Transferencias transferencia = new Transferencias();
        transferencia.setCuentaOrigen(12345678);
        transferencia.setCuentaDestino(87654321);
        transferencia.setMonto(50000);
        transferencia.setMoneda(TipoMoneda.DOLARES);

        when(clienteDao.getClienteByCuentaId(12345678)).thenReturn(cliente);
        when(clienteDao.getClienteByCuentaId(87654321)).thenReturn(cliente);
        when(clienteDao.updateCliente(eq(12345678L), any(Cliente.class))).thenReturn(cliente);
        doNothing().when(cuentaBancariaDao).addTransferInBank(any(Transferencias.class));

        transferenciasDao.transferInBank(transferencia);

        transferenciasDao.deleteTransfers();
    }
}
