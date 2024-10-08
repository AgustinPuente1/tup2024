package ar.edu.utn.frbb.tup.controllers.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controllers.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;

@Component
public class ClienteValidator {
    public void validate(ClienteDto clienteDto) throws DatoNoValidoException { 
        validateNombreApellido(clienteDto.getNombre(), clienteDto.getApellido());
        validateDni(clienteDto.getDni());
        validateFechaNacimiento(clienteDto.getFechaNacimiento());
        validateMail(clienteDto.getMail());
        validateTelefono(clienteDto.getTelefono());
        validateTipo(clienteDto.getTipo());
    }

    private void validateNombreApellido(String nombre, String apellido) throws DatoNoValidoException {
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
            throw new DatoNoValidoException(nombre + " no es un nombre valido");
        } else if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
            throw new DatoNoValidoException(apellido + " no es un apellido valido");
        }
    }

    private void validateDni(long dni) throws DatoNoValidoException {
        if (dni < 1) {
            throw new DatoNoValidoException("DNI no puede ser negativo o 0");
        } else if (String.valueOf(dni).length() > 8) {
            throw new DatoNoValidoException("DNI debe ser de 8 digitos o menos");
        }
    }

    private void validateFechaNacimiento(LocalDate fechaNacimiento) throws DatoNoValidoException {
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new DatoNoValidoException("La fecha de nacimiento no puede ser posterior a la fecha actual");
        }
    }

    private void validateMail(String mail) throws DatoNoValidoException{
        if (!mail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new DatoNoValidoException(mail + " no es un mail valido");
        }
    }

    private void validateTelefono(String telefono) throws DatoNoValidoException{
        if (!telefono.matches("^\\+?[0-9]{9,15}$")) {
            throw new DatoNoValidoException(telefono + " no es un telefono valido");
        }
    }

    private void validateTipo(String tipo) throws DatoNoValidoException{
        if (!tipo.equalsIgnoreCase("F") && !tipo.equalsIgnoreCase("J")
            && !tipo.equalsIgnoreCase("FISICA") && !tipo.equalsIgnoreCase("JURIDICA")) {
            throw new DatoNoValidoException(tipo + " no es un tipo valido");
        }
    }
}
