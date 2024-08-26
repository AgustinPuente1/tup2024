package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.time.Period;

public class Persona {
    private String nombre;
    private String apellido;
    private long dni;
    private LocalDate fechaNacimiento;
    private Direccion domicilio;
    private String mail;
    private String telefono;


    public Persona(String nombre, String apellido, long dni, LocalDate fechaNacimiento, Direccion domicilio,
            String mail, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.mail = mail;
        this.telefono = telefono;
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
    public void setDni(long dni) {
        this.dni = dni;
    }



    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }



    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }



    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Direccion getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(Direccion domicilio) {
        this.domicilio = domicilio;
    }


    public int getEdad() {
        LocalDate hoy = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, hoy);
        return edad.getYears();
    }
    
}
