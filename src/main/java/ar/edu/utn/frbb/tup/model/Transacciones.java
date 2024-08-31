package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;

import ar.edu.utn.frbb.tup.controllers.dto.TransaccionesDto;

public class Transacciones {
    //SOLO EN PESOS
    private long idCuenta;
    private long idTransaccion;
    private LocalDate fecha; 
    private TipoTransaccion tipo;  
    private String descripcion;
    private float monto;

    public Transacciones(TransaccionesDto transaccionesDto) {
        this.idCuenta = transaccionesDto.getIdCuenta();
        this.idTransaccion = 0;
        this.fecha = LocalDate.now();
        if (transaccionesDto.getTipo() == "D") {
            this.tipo = TipoTransaccion.DEBITO;
        } else {
            this.tipo = TipoTransaccion.CREDITO;
        }
        this.descripcion = transaccionesDto.getDescripcion();
        this.monto = transaccionesDto.getMonto();
    }

    public long getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(long idCuenta) {
        this.idCuenta = idCuenta;
    }


    public long getIdTransaccion() {
        return idTransaccion;
    }
    public void setIdTransaccion(long idTransaccion) {
        this.idTransaccion = idTransaccion;
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
