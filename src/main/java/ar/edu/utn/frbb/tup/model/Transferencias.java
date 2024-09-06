package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;

import ar.edu.utn.frbb.tup.controllers.dto.TransferenciasDto;

public class Transferencias {
    private long cuentaOrigen; 
    private long cuentaDestino;
    private float monto;
    private TipoMoneda moneda;
    private LocalDate fechaHora;
    private float comision;

    public Transferencias(TransferenciasDto transferenciasDto) {
        this.cuentaOrigen = transferenciasDto.getCuentaOrigen();
        this.cuentaDestino = transferenciasDto.getCuentaDestino();
        this.monto = transferenciasDto.getMonto();
        if (transferenciasDto.getMoneda().equalsIgnoreCase("USD") 
            || transferenciasDto.getMoneda().equalsIgnoreCase("DOLARES")) {
            this.moneda = TipoMoneda.DOLARES;
        } else {
            this.moneda = TipoMoneda.PESOS;
        }
        this.fechaHora = LocalDate.now();
        this.comision = 0;
    }


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

    public TipoMoneda getMoneda() {
        return moneda;
    }
    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }


    public float getComision() {
        return comision;
    }
    public void setComision(float comision) {
        this.comision = comision;
    }

    
    
}
