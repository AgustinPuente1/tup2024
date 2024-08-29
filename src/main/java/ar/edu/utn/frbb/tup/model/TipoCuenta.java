package ar.edu.utn.frbb.tup.model;

public enum TipoCuenta {
    CUENTA_CORRIENTE("CC"),
    CAJA_AHORRO("CA");

    private final String value;

    public String getValue(){
        return value;
    }

    TipoCuenta(String value) {
        this.value = value;
    }

    public static TipoCuenta fromString(String text) throws IllegalArgumentException{
        for (TipoCuenta tipo : TipoCuenta.values()) {
            if (tipo.value.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un TipoCuenta con el valor: " + text);
    }
}
