package ar.edu.utn.frbb.tup.service;

import java.util.List;

import ar.edu.utn.frbb.tup.controllers.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.EdadNoValidaException;


public interface ClienteService {
    
    List<Cliente> obtenerAllClientes();

    Cliente obtenerCliente(long dni) throws ClienteNoExisteException;

    Cliente crearCliente(ClienteDto clienteDto) throws ClienteAlreadyExistsException, EdadNoValidaException;

    Cliente actualizarCliente(long dni, ClienteDto clienteDto) throws ClienteNoExisteException, EdadNoValidaException;

    void borrarCliente(long dni) throws ClienteNoExisteException;
}
