package ar.edu.utn.frbb.tup.service;

import java.util.List;

import ar.edu.utn.frbb.tup.controllers.dto.TransferenciasDto;
import ar.edu.utn.frbb.tup.model.Recibo;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentasIgualesException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.model.exceptions.SaldoNoValidoException;

public interface TransferenciasService {
    List<Transferencias> obtenerAllTransferencias();
    
    Recibo crearTransferencia(TransferenciasDto transferenciasDto) throws CuentasIgualesException, MontoNoValidoException, CuentaNoExisteException, SaldoNoValidoException, ClienteNoExisteException;
}
