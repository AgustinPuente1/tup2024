package ar.edu.utn.frbb.tup.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.controllers.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.EdadNoValidaException;
import ar.edu.utn.frbb.tup.service.imp.ClienteServiceImp;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ClienteServiceImp clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerAllClientes() {
        //Test para ver si invoca al metodo getAllClientes de la clase de persistencia
        List<Cliente> expectedClientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteDao.getAllClientes()).thenReturn(expectedClientes);

        List<Cliente> actualClientes = clienteService.obtenerAllClientes();

        assertEquals(expectedClientes, actualClientes);
        verify(clienteDao, times(1)).getAllClientes();
    }

    @Test
    public void testObtenerClienteExistente() throws ClienteNoExisteException {
        //Test para ver si invoca al metodo getCliente de la clase de persistencia
        Cliente expectedCliente = new Cliente();
        when(clienteDao.getCliente(12345678L)).thenReturn(expectedCliente);

        Cliente actualCliente = clienteService.obtenerCliente(12345678L);

        assertEquals(expectedCliente, actualCliente);
        verify(clienteDao, times(1)).getCliente(12345678L);
    }

    @Test
    public void testObtenerClienteNoExistente() throws ClienteNoExisteException {
        //Test para ver que invoque al metodo getCliente de la clase de persistencia y de la exception
        when(clienteDao.getCliente(12345678L)).thenThrow(new ClienteNoExisteException("Cliente no existe"));

        assertThrows(ClienteNoExisteException.class, () -> clienteService.obtenerCliente(12345678L));
        verify(clienteDao, times(1)).getCliente(12345678L);
    }

    @Test
    public void testCrearCliente() throws ClienteAlreadyExistsException, EdadNoValidaException {
        //Test para ver si crea cliente
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        clienteDto.setDni(12345678L);
        clienteDto.setTipo("F");
        clienteDto.setNombre("Agustin");
        clienteDto.setApellido("Puente");
        clienteDto.setMail("a@a.com");
        clienteDto.setTelefono("123456789");

        Cliente expectedCliente = new Cliente(clienteDto);
        when(clienteDao.createCliente(any(Cliente.class))).thenReturn(expectedCliente);

        Cliente actualCliente = clienteService.crearCliente(clienteDto);

        assertEquals(expectedCliente, actualCliente);
        verify(clienteDao, times(1)).createCliente(any(Cliente.class));
    }

    @Test
    public void testCrearClienteEdadNoValida() throws ClienteAlreadyExistsException {
        //Test para ver que no cree cliente menor
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFechaNacimiento(LocalDate.now().minusYears(17));

        assertThrows(EdadNoValidaException.class, () -> clienteService.crearCliente(clienteDto));
        verify(clienteDao, never()).createCliente(any(Cliente.class));
    }

    @Test
    public void testActualizarCliente() throws ClienteNoExisteException, EdadNoValidaException {
        //Test para ver si actualiza cliente
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        clienteDto.setDni(12345678L);
        clienteDto.setTipo("F");
        clienteDto.setNombre("Agustin");
        clienteDto.setApellido("Puente");
        clienteDto.setMail("a@a.com");
        clienteDto.setTelefono("123456789");

        Cliente expectedCliente = new Cliente(clienteDto);
        when(clienteDao.updateCliente(eq(12345678L), any(Cliente.class))).thenReturn(expectedCliente);

        Cliente actualCliente = clienteService.actualizarCliente(12345678L, clienteDto);

        assertEquals(expectedCliente, actualCliente);
        verify(clienteDao, times(1)).updateCliente(eq(12345678L), any(Cliente.class));
    }

    @Test
    public void testBorrarCliente() throws ClienteNoExisteException {
        //Test para ver si borra cliente
        doNothing().when(clienteDao).deleteCliente(12345678L);

        clienteService.borrarCliente(12345678L);

        verify(clienteDao, times(1)).deleteCliente(12345678L);
    }
}
