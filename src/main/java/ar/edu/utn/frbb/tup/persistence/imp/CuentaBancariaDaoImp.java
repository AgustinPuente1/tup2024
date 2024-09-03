package ar.edu.utn.frbb.tup.persistence.imp;

import org.springframework.stereotype.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;
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
    public List<CuentaBancaria> getCuentasBancariasByDni(long dni) throws CuentaNoExisteException {
        // Filtra las cuentas y recolecta todas las que tiene de titular el dni
        List<CuentaBancaria> cuentas = findCuentas();
        List<CuentaBancaria> cuentasXDni = cuentas.stream()
            .filter(cuenta -> cuenta.getTitular() == dni)
            .collect(Collectors.toList());

        if (cuentasXDni.isEmpty()) {
            throw new CuentaNoExisteException("No se encontraron cuentas bancarias para el DNI " + dni);
        }

        return cuentasXDni;    
    }

    @Override
    public CuentaBancaria getCuentaBancariaById(long id) throws CuentaNoExisteException {
        // Busca la primera cuenta que coincida con el id 
        List<CuentaBancaria> cuentas = findCuentas();
        return cuentas.stream()
                .filter(cuenta -> cuenta.getIdCuenta() == id)
                .findFirst()
                .orElseThrow(() -> new CuentaNoExisteException("La cuenta bancaria con ID " + id + " no existe."));
    }

    @Override
    public List<Transacciones> getTransaccionesById(long id) throws CuentaNoExisteException {
        CuentaBancaria cuenta = getCuentaBancariaById(id);
        return cuenta.getTransacciones();
    }

    @Override
    public List<Transferencias> getTransferenciasById(long id) throws CuentaNoExisteException {
        CuentaBancaria cuenta = getCuentaBancariaById(id);
        return cuenta.getTransferencias();
    }

    @Override
    public CuentaBancaria createCuentaBancaria(CuentaBancaria cuentaBancaria) {
        List<CuentaBancaria> cuentas = findCuentas();
        while (cuentaBancaria.getIdCuenta() != 0) {
            Random randomNum = new Random();
            long randomId = 1000000L + randomNum.nextInt(9000000);
            try {
                getCuentaBancariaById(randomId);
            } catch (CuentaNoExisteException e) {
                cuentaBancaria.setIdCuenta(randomId);
                break;
            }
        }
        cuentas.add(cuentaBancaria);
        saveCuentas(cuentas);
        return cuentaBancaria;
    }

    @Override
    public CuentaBancaria addDeposito(long id, float monto) throws CuentaNoExisteException {
        List<CuentaBancaria> cuentas = findCuentas();
        CuentaBancaria cuenta = getCuentaBancariaById(id);
        cuenta.addDeposito(monto);
        saveCuentas(cuentas);
        return cuenta;
    }
 
    @Override
    public CuentaBancaria addRetiro(long id, float monto) throws CuentaNoExisteException {
        List<CuentaBancaria> cuentas = findCuentas();
        CuentaBancaria cuenta = getCuentaBancariaById(id);
        cuenta.addRetiro(monto);
        saveCuentas(cuentas);
        return cuenta;
    }

    @Override
    public void deleteCuentaBancaria(long id) throws CuentaNoExisteException {
        List<CuentaBancaria> cuentas = findCuentas();
        CuentaBancaria cuenta = getCuentaBancariaById(id);
        cuentas.remove(cuenta);
        saveCuentas(cuentas);
    }
}
