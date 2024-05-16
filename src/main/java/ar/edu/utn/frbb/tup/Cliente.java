package tup2024.src.main.java.ar.edu.utn.frbb.tup;

import java.time.LocalDate;
import java.time.Period;

public class Cliente {
    private String nombre;
    private String apellido;
    private int dni; 
    private LocalDate fechaNacimiento;
    private Direccion direccion;
    private String telefono;
    private String mail;

    public Cliente(String nombre, String apellido, int dni, LocalDate fechaNacimiento, Direccion direccion,
            String telefono, String mail) {
        
        // Validar el nombre y apellido (sol letras)
        if (!validarNombreApellido(nombre) || !validarNombreApellido(apellido)) {
            throw new IllegalArgumentException("El nombre y el apellido solo deben contener letras.");
        }

        // Validar el DNI (debe ser positivo)
        if (dni <= 0) {
            throw new IllegalArgumentException("El DNI debe ser un número positivo");
        }

        // Validar la fecha de nacimiento (debe tener al menos 18 años)
        LocalDate fechaActual = LocalDate.now();
        int edad = Period.between(fechaNacimiento, fechaActual).getYears();
        if (edad < 18){
            throw new IllegalArgumentException("El cliente debe tener al menos 18 años de edad.");
        }

        // Validar el numero de telefono (solo numeros, guiones y +)
        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("El número de teléfono solo puede contener números, guiones y el símbolo +.");
        }

        // Validar el correo electronico (debe contener al menos un arroba)
        if (!validarMail(mail)) {
            throw new IllegalArgumentException("El correo electrónico debe contener un símbolo '@'.");
        }

        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
    }





    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (!validarNombreApellido(nombre)) {
            throw new IllegalArgumentException("El nombre solo deben contener letras.");
        }
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        if (!validarNombreApellido(apellido)) {
            throw new IllegalArgumentException("El apellido solo deben contener letras.");
        }
        this.apellido = apellido;
    }

    private boolean validarNombreApellido(String texto) {
        return texto.matches("[a-zA-Z]+");
    }

    public int getDni() {
        return dni;
    }
    public void setDni(int dni) {
        this.dni = dni;
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
        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("El número de teléfono solo puede contener números, guiones y el símbolo +.");
        }
        this.telefono = telefono;
    }
    private boolean validarTelefono(String telefono) {
        return telefono.matches("[0-9+-]+");
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        if (!validarMail(mail)) {
            throw new IllegalArgumentException("El correo electrónico debe contener un símbolo '@'.");
        }
        this.mail = mail;
    }
    private boolean validarMail(String mail) {
        return mail.contains("@");
    }





    @Override
    public String toString() {
        return "Cliente [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", fechaNacimiento="
                + fechaNacimiento + ", direccion=" + direccion + ", telefono=" + telefono + ", mail=" + mail + "]";
    }

    
}
