package ar.edu.utn.frbb.tup.controllers.dto;

public class TransferenciasDto {
    private long cuentaOrigen;
    private long cuentaDestino;
    private float monto;
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
