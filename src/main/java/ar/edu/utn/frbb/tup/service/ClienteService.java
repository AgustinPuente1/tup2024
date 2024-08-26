package ar.edu.utn.frbb.tup.service;

import java.time.LocalDate;
import java.time.Period;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;

public class ClienteService {
    public static void agregarCliente(Cliente cliente) throws ClienteAlreadyExistsException{
        if (ClienteDao.existCliente(cliente)){
            throw new ClienteAlreadyExistsException("Ya existe el cliente que quiere agregar");
        }

        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(cliente.getFechaNacimiento(), fechaActual);
        if (periodo.getYears() < 18){
            throw new IllegalArgumentException("El cliente debe ser mayor a 18 años");
        }

        ClienteDao.saveCliente(cliente);
    }
    
    public void agregarCuenta(){

    }

    public Cliente buscarClienteXID(Long Dni) throws ClienteNoExisteException{
        Cliente cliente = ClienteDao.findXID(Dni);
        if (cliente == null){
            throw new ClienteNoExisteException("El cliente que se busca no existe");
        } else {
            return cliente;
        }
    }
}
