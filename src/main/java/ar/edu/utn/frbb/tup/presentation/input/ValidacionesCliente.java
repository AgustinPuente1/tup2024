package ar.edu.utn.frbb.tup.presentation.input;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidacionesCliente {
    /*
    TipoCuenta tipo
    String nombre;
    String apellido;
    int dni; 
    LocalDate fechaNacimiento;
    Direccion direccion;
    String telefono;
    String mail;
    */
    public static boolean validarTipo(String tipo){
        return tipo.toUpperCase() == "F" || tipo.toUpperCase() == "J";
    }

    public static boolean validarNombreApellido(String texto) {
        return texto.matches("[a-zA-Z]+");
    }

    public static boolean validarDni(String dniStr){
        //return dni > 0;
        try {
            long numero = Long.parseLong(dniStr);
            if (numero > 0 && numero <= 60000000){
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static long parsearLong(String dniStr){
        return Long.parseLong(dniStr);
    }
    public static boolean validarCuit(String cuitStr){
        //return dni > 0;
        try {
            long numero = Long.parseLong(cuitStr);
            if (numero >= 10000000000L && numero < 100000000000L){
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarFecha(String fechaStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            @SuppressWarnings("unused")
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static LocalDate asignarFecha(String fechaStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = LocalDate.parse(fechaStr, formatter);
        return fecha;
    }

    public static boolean validarCalleDirecc(String calle){
        return calle.matches("[a-zA-Z]+");
    }
    public static boolean validarAlturaDirecc(String altura) {
        try {
            int numero = Integer.parseInt(altura);
            if (numero >= 1 && numero <= 10000){
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarTelefono(String telefono) {
        return telefono.matches("[0-9+-]+");
    }

    public static boolean validarMail(String mail) {
        return mail.contains("@");
    }

    /*  LocalDate fechaActual = LocalDate.now();
            int edad = Period.between(fechaNacimiento, fechaActual).getYears();
            if (edad < 18){
                throw new IllegalArgumentException("El cliente debe tener al menos 18 años de edad.");
            }
    
        // Validar el correo electronico (debe contener al menos un arroba)
        if (!validarMail(mail)) {
            throw new IllegalArgumentException("El correo electrónico debe contener un símbolo '@'.");
        }
    */
}
