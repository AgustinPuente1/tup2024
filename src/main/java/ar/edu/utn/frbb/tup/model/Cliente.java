package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Cliente{
    private TipoCliente tipo;
    private String nombre;
    private String apellido;
    private long dni; 
    private long cuit;
    private LocalDate fechaNacimiento;
    private Direccion direccion;
    private String telefono;
    private String mail;
    private List<CuentaBancaria> cuentas;

    //TIPO FISICO

    public Cliente(TipoCliente tipo, String nombre, String apellido, long dni, LocalDate fechaNacimiento, Direccion direccion, 
        String telefono, String mail) {
        
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.cuentas = new ArrayList<>();
    }

    //TIPO JURIDICO

    public Cliente(TipoCliente tipo, String nombre,long cuit, LocalDate fechaNacimiento, Direccion direccion, String telefono, String mail){
        this.tipo = tipo;
        this.nombre = nombre;
        this.cuit = cuit;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.cuentas = new ArrayList<>();
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return dni;
    }
    public void setDni(int dni) {
        this.dni = dni;
    }

    public long getCuit() {
        return cuit;
    }
    public void setCuit(int cuit) {
        this.cuit = cuit;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public Direccion getDireccion() {
        return direccion;
    }
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public List<CuentaBancaria> getCuentas() {
        return cuentas;
    }

    @Override
    public String toString() {
        return "Cliente [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", fechaNacimiento="
                + fechaNacimiento + ", direccion=" + direccion + ", telefono=" + telefono + ", mail=" + mail + "]";
    }


    
}
