package ar.edu.utn.frbb.tup.controllers.dto;

import jakarta.validation.constraints.NotNull;

public class DepositoRetiroDto {
    @NotNull(message = "El monto no puede ser nulo")
    float monto;

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
}
