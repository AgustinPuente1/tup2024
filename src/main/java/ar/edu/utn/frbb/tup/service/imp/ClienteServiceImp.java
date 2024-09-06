package ar.edu.utn.frbb.tup.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

import ar.edu.utn.frbb.tup.controllers.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaBancariaDao;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.model.exceptions.EdadNoValidaException;

@Service
public class ClienteServiceImp implements ClienteService {

    private ClienteDao clienteDao;
    private CuentaBancariaDao cuentaBancariaDao;

    @Autowired
    public ClienteServiceImp(ClienteDao clienteDao, CuentaBancariaDao cuentaBancariaDao) {
        this.clienteDao = clienteDao;
        this.cuentaBancariaDao = cuentaBancariaDao;
    }



    @Override
    public List<Cliente> obtenerAllClientes() {
        return clienteDao.getAllClientes();
    }

    @Override
    public Cliente obtenerCliente(long dni) throws ClienteNoExisteException { 
        return clienteDao.getCliente(dni);
    }

    @Override
    public Cliente crearCliente(ClienteDto clienteDto) throws ClienteAlreadyExistsException, EdadNoValidaException {
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaLimite = fechaHoy.minus(Period.ofYears(18));
        
        if (clienteDto.getFechaNacimiento().isAfter(fechaLimite)){
            throw new EdadNoValidaException("No se pueden registrar personas de menos de 18 años");
        }
        Cliente cliente = new Cliente(clienteDto);
        try {
            return clienteDao.createCliente(cliente);
        } catch (ClienteAlreadyExistsException e) {
            throw e;
        }
    }

    @Override
    public Cliente actualizarCliente(long dni,ClienteDto clienteDto) throws ClienteNoExisteException, EdadNoValidaException {
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaLimite = fechaHoy.minus(Period.ofYears(18));
        
        if (clienteDto.getFechaNacimiento().isAfter(fechaLimite)){
            throw new EdadNoValidaException("No se pueden registrar personas de menos de 18 años");
        }

        //ACA BORRA LAS CUENTAS
        Cliente cliente = new Cliente(clienteDto);
        try {
            return clienteDao.updateCliente(dni, cliente);
        } catch (ClienteNoExisteException e) {
            throw e;
        }
    }

    @Override
    public void borrarCliente(long dni) throws ClienteNoExisteException, CuentaNoExisteException {
        cuentaBancariaDao.deleteCuentasPorTitular(dni);
        try {
            clienteDao.deleteCliente(dni);
        } catch (ClienteNoExisteException e) {
            throw e;
        }
    }


}
