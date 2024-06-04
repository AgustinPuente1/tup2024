package ar.edu.utn.frbb.tup.persistence;

import java.util.Map;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.entity.ClienteEntity;

public class ClienteDao {
    public static void saveCliente(Cliente cliente){
        ClienteEntity.save(cliente);
    }

    public static Cliente findXID(Long id){
        Map<Long, Cliente> clientesDataBase = ClienteEntity.dataBase();
        if (clientesDataBase.containsKey(id)){
            return clientesDataBase.get(id);
        }
        return null;
    }

    public static Map<Long, Cliente> findClientes(){
        return ClienteEntity.dataBase();
    }

    public static boolean existCliente(Cliente cliente){
        Map<Long, Cliente> clientesDataBase = findClientes();
        if (clientesDataBase.containsValue(cliente)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean existClienteXID(Long id){
        Map<Long, Cliente> clientesDataBase = findClientes();
        if (clientesDataBase.containsKey(id)){
            return true;
        } else {
            return false;
        }
    }
}
