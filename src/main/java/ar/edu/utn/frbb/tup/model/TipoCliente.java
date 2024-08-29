package ar.edu.utn.frbb.tup.model;

public enum TipoCliente {
    PERSONA_FISICA("F"),
    PERSONA_JURIDICA("J");

    private final String value;

    public String getValue() {
        return value;
    }

    TipoCliente(String value) {
        this.value = value;
    }

    public static TipoCliente fromString(String text) throws IllegalArgumentException{
        for (TipoCliente tipo : TipoCliente.values()) {
            if (tipo.value.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un TipoCliente con el valor: " + text);
    }
}
