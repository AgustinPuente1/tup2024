package ar.edu.utn.frbb.tup.controllers.Dto;

import ar.edu.utn.frbb.tup.model.TipoMoneda;

public class TransferenciasDto {
    private long cuentaOrigen;
    private long cuentaDestino;
    private float monto;
    private TipoMoneda moneda;

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

    
}
