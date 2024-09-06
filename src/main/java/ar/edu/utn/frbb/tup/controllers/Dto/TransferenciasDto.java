package ar.edu.utn.frbb.tup.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransferenciasDto {
    @NotNull(message = "La cuenta de origen no puede ser nula")
    private long cuentaOrigen;
    @NotNull(message = "La cuenta de destino no puede ser nula")
    private long cuentaDestino;
    @NotNull(message = "El monto no puede ser nulo")
    private float monto;
    @NotBlank(message = "La moneda no puede ser vacio")
    private String moneda;

    public long getCuentaOrigen() {
        return cuentaOrigen;
    }
    public void setCuentaOrigen(long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }


    public long getCuentaDestino() {
        return cuentaDestino;
    }
    public void setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }


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
