package ar.edu.utn.frbb.tup.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DepositoRetiroDto {
    @NotNull(message = "El monto no puede ser nulo")
    float monto;
    @NotBlank(message = "La moneda no puede ser vacia")
    String moneda;

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
    
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
