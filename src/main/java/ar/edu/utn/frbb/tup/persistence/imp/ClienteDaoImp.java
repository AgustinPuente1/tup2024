package ar.edu.utn.frbb.tup.persistence.imp;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;

@Repository
public class ClienteDaoImp implements ClienteDao{
    private static final String JSON_FILE_PATH = "/jsonBase/clientes.json";

    private ObjectMapper objectMapper;

    public ClienteDaoImp() {
        this.objectMapper = new ObjectMapper();
    }


    public void saveClientes(List<Cliente> clientes) {
        try {
            // Escribir la lista de clientes en el archivo JSON
            objectMapper.writeValue(new File(JSON_FILE_PATH), clientes);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar el error adecuadamente en un entorno real
        }
    }

    public List<Cliente> findClientes() {
        try {
            File file = new File(JSON_FILE_PATH);
            if (!file.exists()) {
                return List.of();
            }
            // Leer la lista de clientes desde el archivo JSON
            return objectMapper.readValue(file, new TypeReference<List<Cliente>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Devuelve una lista vacía en caso de error
        }
    }



    @Override
    public List<Cliente> getAllClientes() {
        return findClientes();
    }


    @Override
    public Cliente getCliente(long dni) throws ClienteNoExisteException {
        List<Cliente> clientes = findClientes();
        //.steam convierte la lista en un stream para poder filtrar y todo eso
        return clientes.stream().filter(cliente -> cliente.getDni() == dni).findFirst().orElseThrow(() -> new ClienteNoExisteException("No se encontro el cliente con dni: " + dni));
    }

    @Override
    public Cliente createCliente(Cliente cliente) throws ClienteAlreadyExistsException{
        List<Cliente> clientes = findClientes();
        if (clientes.stream().anyMatch(clienteExistente -> clienteExistente.getDni() == cliente.getDni())) {
            throw new ClienteAlreadyExistsException("El cliente con dni: " + cliente.getDni() + " ya existe");
        }
        clientes.add(cliente);
        saveClientes(clientes);
        return cliente;
    }


    @Override
    public Cliente updateCliente(Cliente cliente) throws ClienteNoExisteException {
        List<Cliente> clientes = findClientes();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni() == cliente.getDni()) {
                clientes.set(i, cliente);
                saveClientes(clientes);
                return cliente;
            }
        }
        throw new ClienteNoExisteException("No se encontro el cliente con dni: " + cliente.getDni());
    }

    @Override
    public void deleteCliente(long dni) throws ClienteNoExisteException {
        List<Cliente> clientes = findClientes();
        if (!clientes.stream().anyMatch(cliente -> cliente.getDni() == dni)) {
            throw new ClienteNoExisteException("No se encontro el cliente con dni: " + dni);
        }
        clientes.removeIf(cliente -> cliente.getDni() == dni);
        saveClientes(clientes);
    }
}
