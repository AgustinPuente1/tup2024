package ar.edu.utn.frbb.tup.service;

import java.util.List;

import ar.edu.utn.frbb.tup.controllers.dto.TransaccionesDto;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;

public interface TransaccionesService {
    List<Transacciones> obtenerAllTransacciones();

    Transacciones crearTransaccion(TransaccionesDto transaccionesDto) throws MontoNoValidoException, CuentaNoExisteException, MonedaNoCoincideException, ClienteNoExisteException;

    void borrarAllTransacciones();
}
