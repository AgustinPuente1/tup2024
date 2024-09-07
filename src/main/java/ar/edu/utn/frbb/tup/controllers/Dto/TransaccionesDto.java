package ar.edu.utn.frbb.tup.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransaccionesDto {
    @NotNull(message = "La cuenta no puede ser nula")
    private long idCuenta;
    @NotBlank(message = "El tipo no puede ser nulo")
    private String tipo; 
    @NotBlank(message = "La descripción no puede ser nula")
    private String descripcion;
    @NotNull(message = "El monto no puede ser nulo")
    private float monto;


    public long getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(long idCuenta) {
        this.idCuenta = idCuenta;
    }


    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    public float getMonto() {
        return monto;
    }
    public void setMonto(float monto) {
        this.monto = monto;
    }

    
}
