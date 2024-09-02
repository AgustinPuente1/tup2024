package ar.edu.utn.frbb.tup.persistence.imp;

import org.springframework.stereotype.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;

@Repository
public class ClienteDaoImp implements ClienteDao{
    private static final String JSON_FILE_PATH = "src/main/resources/clientes.json";
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();;


    public void saveClientes(List<Cliente> clientes) {
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(clientes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> findClientes() {
        try {
            if (!Files.exists(Paths.get(JSON_FILE_PATH))) {
                return new ArrayList<>(); // Retorna una lista vacía si el archivo no existe
            }

            try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
                Type tipoListaClientes = new TypeToken<List<Cliente>>() {}.getType();
                return gson.fromJson(reader, tipoListaClientes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retorna una lista vacía en caso de error
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
    public Cliente updateCliente(long dni, Cliente cliente) throws ClienteNoExisteException {
        List<Cliente> clientes = findClientes();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni() == dni) {
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
