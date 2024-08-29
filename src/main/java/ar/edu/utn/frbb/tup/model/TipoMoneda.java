package ar.edu.utn.frbb.tup.model;

public enum TipoMoneda {
    PESOS("ARS"),
    DOLARES("USD");

    private final String value;

    public String getValue() {
        return value;
    }

    TipoMoneda(String value) {
        this.value = value;
    }

    public static TipoMoneda fromString(String text) throws IllegalArgumentException{
        for (TipoMoneda tipo : TipoMoneda.values()) {
            if (tipo.value.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un TipoMoneda con el valor: " + text);
    }
}
