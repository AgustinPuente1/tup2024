package ar.edu.utn.frbb.tup.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.persistence.imp.ClienteDaoImp;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDaoTest {

    @InjectMocks
    private ClienteDaoImp clienteDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearYObtenerCliente() throws ClienteAlreadyExistsException, ClienteNoExisteException, CuentaNoExisteException {
        //Testea el create y el get
        Cliente cliente = new Cliente("Juan", "Perez", 12345678L, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        
        try {
            clienteDao.createCliente(cliente);
        } catch (ClienteAlreadyExistsException e) {
            // Do nothing
        }
        Cliente clienteObtenido = clienteDao.getCliente(12345678L);

        assertEquals(cliente.getClass(), clienteObtenido.getClass());
        assertEquals(cliente.getDni(), clienteObtenido.getDni());
        assertEquals(cliente.getMail(), clienteObtenido.getMail());
        assertEquals(cliente.getNombre(), clienteObtenido.getNombre());
        assertEquals(cliente.getApellido(), clienteObtenido.getApellido());
        assertEquals(cliente.getFechaNacimiento(), clienteObtenido.getFechaNacimiento());
        assertEquals(cliente.getTelefono(), clienteObtenido.getTelefono());

        clienteDao.deleteCliente(12345678L);
    }

    @Test
    public void testBorrarCliente() throws ClienteNoExisteException, ClienteAlreadyExistsException, CuentaNoExisteException {
        //Testea si puede borrar por id correctamente
        Cliente cliente = new Cliente("Juan", "Perez", 12345678L, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        try {
            clienteDao.createCliente(cliente);
        } catch (ClienteAlreadyExistsException e) {
            // Do nothing
        }

        clienteDao.deleteCliente(12345678L);

        assertThrows(ClienteNoExisteException.class, () -> {
            clienteDao.getCliente(12345678L);
        });
    }

    @Test
    public void testClienteYaExiste() throws ClienteNoExisteException, CuentaNoExisteException {
        //Testea si cliente existe
        Cliente cliente = new Cliente("Juan", "Perez", 12345678L, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        
        assertThrows(ClienteAlreadyExistsException.class, () -> {
            clienteDao.createCliente(cliente);
            clienteDao.createCliente(cliente);
        });

        clienteDao.deleteCliente(12345678L);
    }

    @Test
    public void testObtenerClienteNoExistente() {
        //Testea que tire la excepcion correcta
        assertThrows(ClienteNoExisteException.class, () -> {
            clienteDao.getCliente(876543218653L);
        });
    }

    @Test
    public void testActualizarCliente() throws ClienteNoExisteException, ClienteAlreadyExistsException, CuentaNoExisteException {
        //Testea el poder actualizar clientes
        Cliente cliente = new Cliente("Juan", "Perez", 12345678L, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");
        try {
            clienteDao.createCliente(cliente);
        } catch (ClienteAlreadyExistsException e) {
            // Do nothing
        }
        Cliente clienteActualizado = new Cliente("Juan", "Lopez", 12345678L, LocalDate.of(1990, 1, 1), "juan@mail.com", "+5491112345678", "F");

        clienteDao.updateCliente(12345678L, clienteActualizado);
        Cliente clienteObtenido = clienteDao.getCliente(12345678L);

        assertEquals("Lopez", clienteObtenido.getApellido());

        clienteDao.deleteCliente(12345678L);
    }  
}
