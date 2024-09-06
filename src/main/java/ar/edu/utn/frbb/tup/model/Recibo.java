package ar.edu.utn.frbb.tup.model;

public class Recibo {
    private TipoEstadoTransfers estado;
    private String mensaje;

    public Recibo(TipoEstadoTransfers estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public Recibo() {}

    public TipoEstadoTransfers getEstado() {
        return estado;
    }
    public void setEstado(TipoEstadoTransfers estado) {
        this.estado = estado;
    }


    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
