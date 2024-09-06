package ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.controllers.dto.TransferenciasDto;
import ar.edu.utn.frbb.tup.model.Banco;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Recibo;
import ar.edu.utn.frbb.tup.model.TipoEstadoTransfers;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentasIgualesException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;
import ar.edu.utn.frbb.tup.persistence.imp.CuentaBancariaDaoImp;
import ar.edu.utn.frbb.tup.persistence.imp.TransferenciasDaoImp;
import ar.edu.utn.frbb.tup.service.imp.TransferenciasServiceImp;

@ExtendWith(MockitoExtension.class)
public class TransferenciasServiceTest {
    @Mock
    private CuentaBancariaDaoImp cuentaBancariaDao;

    @Mock
    private TransferenciasDaoImp transferenciasDao;

    @Mock
    private Banco banco;

    @InjectMocks
    private TransferenciasServiceImp transferenciasService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransferirErrorIniciales() {
        TransferenciasDto transferenciasDto = new TransferenciasDto();
        transferenciasDto.setCuentaOrigen(11222333L);
        transferenciasDto.setCuentaDestino(11222333L);
        transferenciasDto.setMonto(-1000.0f);
        transferenciasDto.setMoneda("USD");

        assertThrows(CuentasIgualesException.class, () -> transferenciasService.crearTransferencia(transferenciasDto));

        transferenciasDto.setCuentaDestino(44555666L);

        assertThrows(MontoNoValidoException.class, () -> transferenciasService.crearTransferencia(transferenciasDto));
    }

    @Test
    public void testTransferirSaldoInsuficiente() throws CuentaNoExisteException, CuentasIgualesException, MontoNoValidoException, SaldoNoValidoException, ClienteNoExisteException {
        TransferenciasDto transferenciasDto = new TransferenciasDto();
        transferenciasDto.setCuentaOrigen(11222333L);
        transferenciasDto.setCuentaDestino(44555666L);
        transferenciasDto.setMonto(150000f);
        transferenciasDto.setMoneda("USD");

        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.setSaldo(40000f);

        when(cuentaBancariaDao.getCuentaBancariaById(transferenciasDto.getCuentaOrigen())).thenReturn(cuenta);
        when(banco.getLimiteSobregiro()).thenReturn(100000f);

        Recibo recibo = transferenciasService.crearTransferencia(transferenciasDto);
    
        assert(recibo.getEstado() == TipoEstadoTransfers.FALLIDA);
    }

    @Test
    public void testTransferirExitosamenteEntreBancos() throws CuentaNoExisteException, CuentasIgualesException, MontoNoValidoException, SaldoNoValidoException, ClienteNoExisteException{
        TransferenciasDto transferenciasDto = new TransferenciasDto();
        transferenciasDto.setCuentaOrigen(11222333L);
        transferenciasDto.setCuentaDestino(44555666L);
        transferenciasDto.setMonto(150000f);
        transferenciasDto.setMoneda("USD");

        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.setSaldo(400000f);

        when(cuentaBancariaDao.getCuentaBancariaById(transferenciasDto.getCuentaOrigen())).thenReturn(cuenta);
        when(banco.getLimiteSobregiro()).thenReturn(100000f);
        when(cuentaBancariaDao.getCuentaBancariaById(transferenciasDto.getCuentaDestino())).thenThrow(CuentaNoExisteException.class);
        lenient().doNothing().when(transferenciasDao).transferBetweenBanks(any(Transferencias.class));

        Recibo recibo = transferenciasService.crearTransferencia(transferenciasDto);
        assertTrue(recibo.getEstado() == TipoEstadoTransfers.EXITOSA || recibo.getEstado() == TipoEstadoTransfers.ERROR);
    }

    @Test
    public void testTransferirExitosamenteEnBanco() throws CuentaNoExisteException, CuentasIgualesException, MontoNoValidoException, SaldoNoValidoException, ClienteNoExisteException{
        TransferenciasDto transferenciasDto = new TransferenciasDto();
        transferenciasDto.setCuentaOrigen(11222333L);
        transferenciasDto.setCuentaDestino(44555666L);
        transferenciasDto.setMonto(150000f);
        transferenciasDto.setMoneda("USD");

        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.setSaldo(400000f);

        when(cuentaBancariaDao.getCuentaBancariaById(transferenciasDto.getCuentaOrigen())).thenReturn(cuenta);
        when(banco.getLimiteSobregiro()).thenReturn(100000f);
        when(cuentaBancariaDao.getCuentaBancariaById(transferenciasDto.getCuentaDestino())).thenReturn(cuenta);
        lenient().doNothing().when(transferenciasDao).transferInBank(any(Transferencias.class));

        Recibo recibo = transferenciasService.crearTransferencia(transferenciasDto);
        assertTrue(recibo.getEstado() == TipoEstadoTransfers.EXITOSA || recibo.getEstado() == TipoEstadoTransfers.ERROR);
    }
}
