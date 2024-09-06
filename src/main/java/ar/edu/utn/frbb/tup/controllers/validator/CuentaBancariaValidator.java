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
        if (dni < 1) {
            throw new DatoNoValidoException("DNI no puede ser negativo o 0");
        } else if (String.valueOf(dni).length() > 8) {
            throw new DatoNoValidoException("DNI debe ser de 8 digitos o menos");
        }
    }

    public void validateTipo(String tipo) throws DatoNoValidoException{
        if (!tipo.equalsIgnoreCase("CC") && !tipo.equalsIgnoreCase("CA") 
            && !tipo.equalsIgnoreCase("CUENTA CORRIENTE") && !tipo.equalsIgnoreCase("CAJA DE AHORRO")){ 
            throw new DatoNoValidoException(tipo + " no es un tipo valido (CC, CA, CUENTA CORRIENTE o CAJA DE AHORRO)");
        }
    }

    public void validateMoneda(String moneda) throws DatoNoValidoException{
        if (!moneda.equalsIgnoreCase("USD") && !moneda.equalsIgnoreCase("ARS") 
                && !moneda.equalsIgnoreCase("DOLARES") && !moneda.equalsIgnoreCase("PESOS")){
            throw new DatoNoValidoException(moneda + " no es una moneda valida (USD, ARS, DOLARES o PESOS)");
        }
    }
}

