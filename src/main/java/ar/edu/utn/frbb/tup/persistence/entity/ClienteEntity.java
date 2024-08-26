package ar.edu.utn.frbb.tup.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.TipoCliente;

public class ClienteEntity{
    protected static Map<Long, Cliente> clientes =  new HashMap<>();

    public static void save(Cliente cliente){
        if (cliente.getTipo() == TipoCliente.PERSONA_FISICA){
            clientes.put(cliente.getDni(), cliente);
        } else {
            clientes.put(cliente.getCuit(), cliente);
        }
    }

    public static Map<Long, Cliente> dataBase(){
        return clientes;
    }
}
