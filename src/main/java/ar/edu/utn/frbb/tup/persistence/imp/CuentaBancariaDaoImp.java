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

import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.persistence.CuentaBancariaDao;

@Repository
public class CuentaBancariaDaoImp implements CuentaBancariaDao {
    private static final String JSON_FILE_PATH = "src/main/resources/cuentasBancarias.json";
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

    public void saveCuentas(List<CuentaBancaria> cuentas) {
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(cuentas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CuentaBancaria> findCuentas() {
        try {
            if (!Files.exists(Paths.get(JSON_FILE_PATH))) {
                return new ArrayList<>(); // Retorna una lista vacía si el archivo no existe
            }

            try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
                Type tipoListaCuentas = new TypeToken<List<CuentaBancaria>>() {}.getType();
                return gson.fromJson(reader, tipoListaCuentas);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retorna una lista vacía en caso de error  
        }
    }

    @Override
    public List<CuentaBancaria> getAllCuentasBancarias() {
        return findCuentas();
    }

    @Override 
    public List<CuentaBancaria> getCuentasBancariasByDni(long id) throws CuentaNoExisteException {
        List<CuentaBancaria> cuentas = findCuentas();
        return cuentas.stream().filter(cuenta -> cuenta.getTitular() == id).findFirst().orElseThrow(() -> new CuentaNoExisteException("No se encontro una cuenta con titular: " + dni));
    }
}
