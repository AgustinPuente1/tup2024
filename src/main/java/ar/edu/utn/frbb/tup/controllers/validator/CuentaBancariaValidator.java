package ar.edu.utn.frbb.tup.controllers.validator;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;

@Component
public class CuentaBancariaValidator {
    public void validate(CuentaBancariaDto cuentaBancariaDto) throws DatoNoValidoException {
        validateTitular(cuentaBancariaDto.getTitular());
        validateTipo(cuentaBancariaDto.getTipoCuenta());
        validateMoneda(cuentaBancariaDto.getMoneda());
    }

    public void validateTitular(long dni) throws DatoNoValidoException {
        if (dni < 0) {
            throw new DatoNoValidoException("DNI no puede ser negativo");
        } else if (String.valueOf(dni).length() > 8) {
            throw new DatoNoValidoException("DNI debe ser de 8 digitos o menos");
        }
    }

    public void validateTipo(String tipo) throws DatoNoValidoException{
        if (!tipo.equalsIgnoreCase("CC") && !tipo.equalsIgnoreCase("CA")) {
            throw new DatoNoValidoException(tipo + " no es un tipo valido");
        }
    }

    public void validateMoneda(String moneda) throws DatoNoValidoException{
        if (!moneda.equalsIgnoreCase("ARS") && !moneda.equalsIgnoreCase("USD")) {
            throw new DatoNoValidoException(moneda + " no es una moneda valida");
        }
    }
}
