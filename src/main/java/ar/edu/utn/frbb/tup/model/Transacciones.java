package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;

import ar.edu.utn.frbb.tup.controllers.dto.TransaccionesDto;

public class Transacciones {
    //SOLO EN PESOS
    private long idCuenta;
    private LocalDate fecha; 
    private TipoTransaccion tipo;  
    private String descripcion;
    private float monto;

    public Transacciones(TransaccionesDto transaccionesDto) {
        this.idCuenta = transaccionesDto.getIdCuenta();
        this.fecha = LocalDate.now();
        if (transaccionesDto.getTipo().equalsIgnoreCase("D")
                || transaccionesDto.getTipo().equalsIgnoreCase("DEBITO")) {
            this.tipo = TipoTransaccion.DEBITO;
        } else {
            this.tipo = TipoTransaccion.CREDITO;
        }
        this.descripcion = transaccionesDto.getDescripcion();
        this.monto = transaccionesDto.getMonto();
    }

    //Constructor para test

    public Transacciones(){
        this.fecha = LocalDate.now();
    }

    public long getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


    public TipoTransaccion getTipo() {
        return tipo;
    }
    public void setTipo(TipoTransaccion tipo) {
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
