package ar.edu.utn.frbb.tup.persistence.imp;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.TipoTransaccion;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaBancariaDao;
import ar.edu.utn.frbb.tup.persistence.TransaccionesDao;

@Repository
public class TransaccionesDaoImp implements TransaccionesDao {
    private static final String JSON_FILE_PATH = "src/main/resources/transacciones.json";
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

    private CuentaBancariaDao cuentaBancariaDao;
    private ClienteDao clienteDao;

    @Autowired
    public TransaccionesDaoImp(CuentaBancariaDao cuentaBancariaDao, ClienteDao clienteDao) {
        this.cuentaBancariaDao = cuentaBancariaDao;
        this.clienteDao = clienteDao;
    }

    public void saveTransacciones(List<Transacciones> transacciones) {
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(transacciones, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Transacciones> findTransacciones() {
        try {
            if (!Files.exists(Paths.get(JSON_FILE_PATH))) {
                return new ArrayList<>(); // Retorna una lista vacía si el archivo no existe
            }

            try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
                Type tipoListatransacciones = new TypeToken<List<Transacciones>>() {}.getType();
                return gson.fromJson(reader, tipoListatransacciones);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retorna una lista vacía en caso de error  
        }
    }

    @Override
    public List<Transacciones> getAllTransacciones() {
        return findTransacciones();
    }

    @Override
    public Transacciones createTransaccion(Transacciones transaccion) throws ClienteNoExisteException{
        List<Transacciones> transacciones = findTransacciones();

        Cliente cliente = clienteDao.getClienteByCuentaId(transaccion.getIdCuenta());
        
        //Agrega a clientes.json
        for (CuentaBancaria c : cliente.getCuentas()) {
            if (c.getIdCuenta() == transaccion.getIdCuenta()) {
                if (transaccion.getTipo().equals(TipoTransaccion.CREDITO)){
                    c.setSaldo(c.getSaldo() - transaccion.getMonto());
                } else {
                    c.setSaldo(c.getSaldo() + transaccion.getMonto());
                }
                c.addTransacciones(transaccion);
                break;
            }
        }

        clienteDao.updateCliente(cliente.getDni(), cliente);

        //Agrega a cuentaBancarias.json
        cuentaBancariaDao.addTransaccion(transaccion);

        //Agrega a transacciones.json
        transacciones.add(transaccion);
        saveTransacciones(transacciones);
        return transaccion;
    }




    @Override
    public void deleteTransacciones(){
        saveTransacciones(new ArrayList<>());
    }
}
