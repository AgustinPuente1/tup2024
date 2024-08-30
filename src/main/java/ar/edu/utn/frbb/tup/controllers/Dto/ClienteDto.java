package ar.edu.utn.frbb.tup.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public class ClienteDto extends PersonaDto{
    @NotBlank(message = "El tipo de cliente es obligatorio (Fisica 'F' o Juridica 'J')")
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
