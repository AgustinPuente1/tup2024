package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;

import java.util.List;

public interface ClienteDao {
    List<Cliente> getAllClientes();

    Cliente getCliente(long dni) throws ClienteNoExisteException;

    Cliente createCliente(Cliente cliente) throws ClienteAlreadyExistsException;

    Cliente updateCliente(long dni, Cliente cliente) throws ClienteNoExisteException;

    void deleteCliente(long dni) throws ClienteNoExisteException, CuentaNoExisteException;

}
