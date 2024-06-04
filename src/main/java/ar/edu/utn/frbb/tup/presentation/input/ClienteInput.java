package ar.edu.utn.frbb.tup.presentation.input;

import java.time.LocalDate;
import java.util.Scanner;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Direccion;
import ar.edu.utn.frbb.tup.model.TipoCliente;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.ClienteService;

public class ClienteInput extends LimpiarScreen{
    public void crearCliente(){
        clearScreen();

        //        TIPO

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese tipo de cliente: ('F' para una persona fisica y 'J' para una persona juridica)");
        String tipoCuentaStr = scanner.nextLine();
        while (!ValidacionesCliente.validarTipo(tipoCuentaStr)){
            System.out.println("Error, ingresar unos de los tipo disponibles... ('F' o 'J'): ");
            tipoCuentaStr = scanner.nextLine();
        }

        if (tipoCuentaStr.toUpperCase() == "F"){
            crearClienteFisico();
        } else if (tipoCuentaStr.toUpperCase() == "J"){
            crearClienteJuridico();
        }

        scanner.close();
    }
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
    private void crearClienteFisico(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre: ");
        String nombreStr = scanner.nextLine();
        while (!ValidacionesCliente.validarNombreApellido(nombreStr)){
            System.out.println("Error, ingrese un nombre valido (Solo letras): ");
            nombreStr = scanner.nextLine();
        }

        System.out.println("Ingrese el apellido: ");
        String apellidoStr = scanner.nextLine();
        while (!ValidacionesCliente.validarNombreApellido(apellidoStr)){
            System.out.println("Error, ingrese un apellido valido (Solo letras): ");
            apellidoStr = scanner.nextLine();
        }

        System.out.println("Ingrese su DNI: ");
        String dniStr = scanner.nextLine();
        while (!ValidacionesCliente.validarDni(dniStr)) {
            System.out.println("Error, ingresar un DNI valido (Solo numeros del 0 al 60.000.00, sin puntos): ");
            dniStr = scanner.nextLine();
        }
        Long dni = ValidacionesCliente.parsearLong(dniStr);
        if (ClienteDao.existClienteXID(dni)){
            System.out.println("Error, el DNI que ingreso ya esta registrado como cliente.");
            scanner.close();
            return;
        }

        System.out.println("Ingrese fecha de nacimiento: (YYYY-MM-DD)");
        String fechaStr = scanner.nextLine();
        while (!ValidacionesCliente.validarFecha(fechaStr)){
            System.out.println("Error, ingrese una fecha valida (Ejemplo: 2000-01-01)...");
            fechaStr = scanner.nextLine();
        }
        LocalDate fecha = ValidacionesCliente.asignarFecha(fechaStr);

        System.out.println("Ingrese su Calle (Letras): ");
        String calleStr = scanner.nextLine();
        while (!ValidacionesCliente.validarCalleDirecc(calleStr)){
            System.out.println("Error, ingrese una calle valida (solo letras): ");
            calleStr = scanner.nextLine();
        }

        System.out.println("Ingrese la altura de la calle (solo numeros): ");
        String alturaStr = scanner.nextLine();
        while (!ValidacionesCliente.validarAlturaDirecc(alturaStr)){
            System.out.println("Error, ingrese una altura de calle valida (Solo numero del 1 al 10000): ");
            alturaStr = scanner.nextLine();
        }
        int altura = Integer.parseInt(alturaStr);

        System.out.println("Ingrese un telefono (solamente numeros y signo '+'): ");
        String telefonoStr = scanner.nextLine();
        while (!ValidacionesCliente.validarTelefono(telefonoStr)) {
            System.out.println("Error, ingrese un telefono valido (solamente numeros y signo '+'): ");
            telefonoStr = scanner.nextLine();
        }

        System.out.println("Ingrese un mail (debe contener un '@'): ");
        String mailStr = scanner.nextLine();
        while(!ValidacionesCliente.validarMail(mailStr)){
            System.out.println("Error, ingrese un mail: ");
            mailStr = scanner.nextLine();
        }

        Cliente cliente = new Cliente(TipoCliente.PERSONA_FISICA,nombreStr,apellidoStr,dni, fecha, new Direccion(calleStr, altura),telefonoStr, mailStr);
        try {
            ClienteService.agregarCliente(cliente);
        } catch (ClienteAlreadyExistsException e) {
            System.out.println("\nEl cliente que ingreso ya existe");
        } catch (IllegalArgumentException e){
            System.out.println("\nEl cliente que ingreso es menor de edad, los menores no pueden ser clientes.");
        }

        scanner.close();
    }
    /*
    TipoCuenta tipo
    String nombre;
    int CUIT
    LocalDate fechaNacimiento;
    Direccion direccion;
    String telefono;
    String mail;
    */
    private void crearClienteJuridico(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre: ");
        String nombreStr = scanner.nextLine();
        while (!ValidacionesCliente.validarNombreApellido(nombreStr)){
            System.out.println("Error, ingrese un nombre valido (Solo letras): ");
            nombreStr = scanner.nextLine();
        }

        System.out.println("Ingrese su CUIT: ");
        String cuitStr = scanner.nextLine();
        while (!ValidacionesCliente.validarCuit(cuitStr)) {
            System.out.println("Error, ingresar un CUIT valido (Numero de 11 digitos): ");
            cuitStr = scanner.nextLine();
        }
        Long cuit = ValidacionesCliente.parsearLong(cuitStr);
        if (ClienteDao.existClienteXID(cuit)){
            System.out.println("Error, el CUIT que ingreso ya esta registrado como cliente.");
            scanner.close();
            return;
        }

        System.out.println("Ingrese fecha de nacimiento de la empresa: (YYYY-MM-DD)");
        String fechaStr = scanner.nextLine();
        while (!ValidacionesCliente.validarFecha(fechaStr)){
            System.out.println("Error, ingrese una fecha valida (Ejemplo: 2000-01-01)...");
            fechaStr = scanner.nextLine();
        }
        LocalDate fecha = ValidacionesCliente.asignarFecha(fechaStr);

        System.out.println("Ingrese su Calle (Letras): ");
        String calleStr = scanner.nextLine();
        while (!ValidacionesCliente.validarCalleDirecc(calleStr)){
            System.out.println("Error, ingrese una calle valida (solo letras): ");
            calleStr = scanner.nextLine();
        }

        System.out.println("Ingrese la altura de la calle (solo numeros): ");
        String alturaStr = scanner.nextLine();
        while (!ValidacionesCliente.validarAlturaDirecc(alturaStr)){
            System.out.println("Error, ingrese una altura de calle valida (Solo numero del 1 al 10000): ");
            alturaStr = scanner.nextLine();
        }
        int altura = Integer.parseInt(alturaStr);

        System.out.println("Ingrese un telefono (solamente numeros y signo '+'): ");
        String telefonoStr = scanner.nextLine();
        while (!ValidacionesCliente.validarTelefono(telefonoStr)) {
            System.out.println("Error, ingrese un telefono valido (solamente numeros y signo '+'): ");
            telefonoStr = scanner.nextLine();
        }

        System.out.println("Ingrese un mail (debe contener un '@'): ");
        String mailStr = scanner.nextLine();
        while(!ValidacionesCliente.validarMail(mailStr)){
            System.out.println("Error, ingrese un mail: ");
            mailStr = scanner.nextLine();
        }

        Cliente cliente = new Cliente(TipoCliente.PERSONA_JURIDICA, nombreStr, cuit, fecha, new Direccion(calleStr, altura), telefonoStr, mailStr);
        try {
            ClienteService.agregarCliente(cliente);
        } catch (ClienteAlreadyExistsException e) {
            System.out.println("\nEl cliente que ingreso ya existe.");
        } catch (IllegalArgumentException e){
            System.out.println("\nEl cliente que ingreso es menor de edad, los menores no pueden ser clientes.");
        }
        scanner.close();
    }
}
