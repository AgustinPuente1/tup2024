package ar.edu.utn.frbb.tup.controllers.validator;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controllers.dto.TransaccionesDto;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;

@Component
public class TransaccionesValidator {
    public void validate(TransaccionesDto transaccionesDto) throws DatoNoValidoException {
        validateIDCuenta(transaccionesDto.getIdCuenta());
        validateTipo(transaccionesDto.getTipo());
    }

    private void validateIDCuenta(long idCuenta) throws DatoNoValidoException {
        if (idCuenta <= 0) {
            throw new DatoNoValidoException("El ID de la cuenta no puede ser negativo o 0");
        }
    }

    private void validateTipo(String tipo) throws DatoNoValidoException {
        if (!tipo.equalsIgnoreCase("DEBITO") && !tipo.equalsIgnoreCase("CREDITO")
            && !tipo.equalsIgnoreCase("D") && !tipo.equalsIgnoreCase("C")) {
            throw new DatoNoValidoException(tipo + " no es un tipo valido (DEBITO o CREDITO)");
        }
    }
}
