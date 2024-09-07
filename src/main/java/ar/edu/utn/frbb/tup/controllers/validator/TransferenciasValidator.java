package ar.edu.utn.frbb.tup.controllers.validator;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controllers.dto.TransferenciasDto;
import ar.edu.utn.frbb.tup.model.exceptions.DatoNoValidoException;

@Component
public class TransferenciasValidator {
    public void validate(TransferenciasDto transferenciasDto) throws DatoNoValidoException {
        validateCuentas(transferenciasDto.getCuentaOrigen(), transferenciasDto.getCuentaDestino());
        validateMoneda(transferenciasDto.getMoneda());
    }

    private void validateCuentas(long titular1, long titular2) throws DatoNoValidoException{
        if (titular1 < 1){
            throw new DatoNoValidoException("El ID de la cuenta origen no puede ser negativo o 0");
        } else if (titular2 < 1){
            throw new DatoNoValidoException("El ID de la cuenta destino no puede ser negativo o 0");
        }
    }

    private void validateMoneda(String moneda) throws DatoNoValidoException{
        if (!moneda.equalsIgnoreCase("USD") && !moneda.equalsIgnoreCase("ARS") 
                && !moneda.equalsIgnoreCase("DOLARES") && !moneda.equalsIgnoreCase("PESOS")){
            throw new DatoNoValidoException("La moneda debe ser USD o ARS");
        }
    }
}
