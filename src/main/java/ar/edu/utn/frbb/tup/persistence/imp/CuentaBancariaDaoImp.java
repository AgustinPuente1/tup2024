package ar.edu.utn.frbb.tup.persistence.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.TipoTransaccion;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaBancariaDao;

@Repository
public class CuentaBancariaDaoImp implements CuentaBancariaDao {
    private static final String JSON_FILE_PATH = "src/main/resources/cuentasBancarias.json";
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();


    private ClienteDao clienteDao;

    @Autowired
    public CuentaBancariaDaoImp(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

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

    /**
     * Crea una nueva cuenta bancaria para el cliente especificado.
     * Genera un id unico para la cuenta.
     * Si el cliente no existe se lanza una excepcion de ClienteNoExisteException.
     * @param cuentaBancaria la cuenta a crear
     * @return la cuenta creada
     * @throws ClienteNoExisteException si el cliente no existe
     */
    @Override
    public CuentaBancaria createCuentaBancaria(CuentaBancaria cuentaBancaria) throws ClienteNoExisteException {
        List<CuentaBancaria> cuentas = findCuentas();
        while (cuentaBancaria.getIdCuenta() == 0) {
            Random randomNum = new Random();
            long randomId = 1000000L + randomNum.nextInt(9000000);
            try {
                getCuentaBancariaById(randomId);
            } catch (CuentaNoExisteException e) {
                cuentaBancaria.setIdCuenta(randomId);
                break;
            }
        }

        Cliente cliente = clienteDao.getCliente(cuentaBancaria.getTitular());
        
        cliente.addCuentas(cuentaBancaria);
        clienteDao.updateCliente(cliente.getDni(), cliente);

        cuentas.add(cuentaBancaria);
        saveCuentas(cuentas);

        return cuentaBancaria;
    }

    /**
     * Agrega un deposito a una cuenta existente.
     * Si la cuenta no existe se lanza una excepcion de CuentaNoExisteException.
     * Si el cliente no existe se lanza una excepcion de ClienteNoExisteException.
     * @param id el id de la cuenta
     * @param monto el monto del deposito
     * @return la cuenta modificada
     * @throws CuentaNoExisteException si la cuenta no existe
     * @throws ClienteNoExisteException si el cliente no existe
     */
    @Override
    public CuentaBancaria addDeposito(Cliente cliente, long id, float monto) throws CuentaNoExisteException, ClienteNoExisteException {
        List<CuentaBancaria> cuentas = findCuentas();
        for (CuentaBancaria c : cliente.getCuentas()) {
            if (c.getIdCuenta() == id) {
                c.addDeposito(monto);
                break;
            }
        }
        for (CuentaBancaria c : cuentas) {
            if (c.getIdCuenta() == id) {
                c.addDeposito(monto);
                break;
            }
        }
        clienteDao.updateCliente(cliente.getDni(), cliente);
        saveCuentas(cuentas);
        CuentaBancaria cuenta = getCuentaBancariaById(id);
        return cuenta;
    }
 
    /**
     * Agrega un retiro a una cuenta existente.
     * Si la cuenta no existe se lanza una excepcion de CuentaNoExisteException.
     * Si el cliente no existe se lanza una excepcion de ClienteNoExisteException.
     * @param id el id de la cuenta
     * @param monto el monto del retiro
     * @return la cuenta modificada
     * @throws CuentaNoExisteException si la cuenta no existe
     * @throws ClienteNoExisteException si el cliente no existe
     */
    @Override
    public CuentaBancaria addRetiro(Cliente cliente, long id, float monto) throws CuentaNoExisteException, ClienteNoExisteException {
        List<CuentaBancaria> cuentas = findCuentas();
        for (CuentaBancaria c : cliente.getCuentas()) {
            if (c.getIdCuenta() == id) {
                c.addRetiro(monto);
                break;
            }
        }
        for (CuentaBancaria c : cuentas) {
            if (c.getIdCuenta() == id) {
                c.addRetiro(monto);
                break;
            }
        }
        clienteDao.updateCliente(cliente.getDni(), cliente);
        saveCuentas(cuentas);
        CuentaBancaria cuenta = getCuentaBancariaById(id);
        return cuenta;
    }

    /**
     * Borra una cuenta bancaria.
     * Si la cuenta no existe se lanza una excepcion de CuentaNoExisteException.
     * Si el cliente no existe se lanza una excepcion de ClienteNoExisteException.
     * @param id el id de la cuenta a borrar
     * @throws CuentaNoExisteException si la cuenta no existe
     * @throws ClienteNoExisteException si el cliente no existe
     */
    @Override
    public void deleteCuentaBancaria(Cliente cliente, long id) throws CuentaNoExisteException, ClienteNoExisteException {
        List<CuentaBancaria> cuentas = findCuentas();
        CuentaBancaria cuentaB = getCuentaBancariaById(id);

        cliente.getCuentas().removeIf(cuenta -> cuenta.getIdCuenta() == id);
        cuentas.removeIf(cuenta -> cuenta.getIdCuenta() == id);
        
        clienteDao.updateCliente(cuentaB.getTitular(), cliente);
        saveCuentas(cuentas);
    }

    /**
     * Borra todas las cuentas bancarias de un cliente.
     * @param dni el DNI del cliente
     */
    @Override
    public void deleteCuentasPorTitular(long dni){
        List<CuentaBancaria> cuentas = findCuentas();

        cuentas.removeIf(cuenta -> cuenta.getTitular() == dni);

        saveCuentas(cuentas);
    }

    /**
     * Agrega una transferencia entre cuentas bancarias.
     * @param transferencia la transferencia a agregar
     */
    @Override 
    public void addTransferBetweenBanks(Transferencias transferencia) {
        List<CuentaBancaria> cuentas = findCuentas();

        for (CuentaBancaria c : cuentas) {
            if (c.getIdCuenta() == transferencia.getCuentaOrigen()) {
                c.setSaldo(c.getSaldo() - transferencia.getMonto() - transferencia.getComision());
                c.addTransferencia(transferencia);
                break;
            }
        }   

        saveCuentas(cuentas);
    }

    /**
     * Agrega una transferencia dentro de la misma entidad bancaria.
     * @param transferencia la transferencia a agregar
     */
    @Override
    public void addTransferInBank(Transferencias transferencia) {
        List<CuentaBancaria> cuentas = findCuentas();

        for (CuentaBancaria c : cuentas) {
            if (c.getIdCuenta() == transferencia.getCuentaOrigen()) {
                c.setSaldo(c.getSaldo() - transferencia.getMonto() - transferencia.getComision());
                c.addTransferencia(transferencia);
            } else if (c.getIdCuenta() == transferencia.getCuentaDestino()) {
                c.setSaldo(c.getSaldo() + transferencia.getMonto());
                c.addTransferencia(transferencia);
            }
        }

        saveCuentas(cuentas);
    }

    /**
     * Agrega una transacci n a una cuenta bancaria.
     * Actualiza el saldo de la cuenta seg n el tipo de transacci n.
     * @param transaccion la transacci n a agregar
     */
    @Override
    public void addTransaccion(Transacciones transaccion) {
        List<CuentaBancaria> cuentas = findCuentas();

        for (CuentaBancaria c : cuentas) {
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

        saveCuentas(cuentas);
    }
}
