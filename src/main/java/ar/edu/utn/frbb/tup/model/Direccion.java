package ar.edu.utn.frbb.tup.model;

public class Direccion {
    private String calle;
    private int altura;
    
    public Direccion(String calle, int altura) {
        this.calle = calle;
        this.altura = altura;
    }

    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    } 
}
