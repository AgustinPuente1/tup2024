package ar.edu.utn.frbb.tup.controllers.Dto;

import ar.edu.utn.frbb.tup.model.TipoCliente;

public class ClienteDto extends PersonaDto{
    private TipoCliente tipo;

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }
}
