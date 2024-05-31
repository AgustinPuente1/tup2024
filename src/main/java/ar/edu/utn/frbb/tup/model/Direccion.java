package ar.edu.utn.frbb.tup.model;

public class Direccion {
    private String calle;
    private int altura;
    
    public Direccion(String calle, int altura) {
        //valida si la calle son letras
        if (!validarCalle(calle)) {
            throw new IllegalArgumentException("El nombre de la calle solo debe contener letras.");
        }

        // valida si la altura son numeros
        if (!validarAltura(altura)) {
            throw new IllegalArgumentException("La altura debe ser un número entre 1 y 10000.");
        }
        this.calle = calle;
        this.altura = altura;
    }

    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        if (!validarCalle(calle)) {
            throw new IllegalArgumentException("El nombre de la calle solo debe contener letras.");
        }
        this.calle = calle;
    }
    private boolean validarCalle(String calle) {
        return calle.matches("[a-zA-Z]+");
    }


    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        if (!validarAltura(altura)) {
            throw new IllegalArgumentException("La altura debe ser un número entre 1 y 10000.");
        }
        this.altura = altura;
    } 
    private boolean validarAltura(int altura) {
        return altura >= 1 && altura <= 10000;
    }
}
