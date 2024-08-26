package ar.edu.utn.frbb.tup.controllers;

import java.time.LocalDate;

import ar.edu.utn.frbb.tup.model.Direccion;

public class PersonaDto {
    private String nombre;
    private String apellido;
    private long dni;
    private LocalDate fechaNacimiento;
    private Direccion domicilio;
    private String mail;
    private String telefono;

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


    public Direccion getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(Direccion domicilio) {
        this.domicilio = domicilio;
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
}
