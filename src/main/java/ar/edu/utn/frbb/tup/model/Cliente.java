package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;

import ar.edu.utn.frbb.tup.controllers.ClienteDto;


public class Cliente extends Persona{
    private TipoCliente tipo; //PERSONA_FISICA o PERSONA_JURIDICA;
    private String banco;
    private LocalDate fechaAlta;
    private Set<CuentaBancaria> cuentas;

    public Cliente(ClienteDto clienteDto){
        super(clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getDni(), clienteDto.getFechaNacimiento(), clienteDto.getDomicilio(), clienteDto.getMail(), clienteDto.getTelefono());
        this.tipo = clienteDto.getTipo();
        this.banco = "Banco TUP";
        this.fechaAlta = LocalDate.now();
        this.cuentas = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Cliente [tipo=" + tipo + ", banco=" + banco + ", fechaAlta=" + fechaAlta + ", cuentas=" + cuentas
                + ", getNombre()=" + getNombre() + ", getApellido()=" + getApellido() + ", getDni()=" + getDni()
                + ", getFechaNacimiento()=" + getFechaNacimiento() + ", getMail()=" + getMail() + ", getClass()="
                + getClass() + ", getTelefono()=" + getTelefono() + ", getDomicilio()=" + getDomicilio()
                + ", getEdad()=" + getEdad() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

=======
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
>>>>>>> ed4412701248b4cdb0fbea8b6abd4383865b38c2

    public TipoCliente getTipo() {
        return tipo;
    }
<<<<<<< HEAD
    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public String getBanco() {
        return banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<CuentaBancaria> getCuentas() {
        return cuentas;
    }
    public void addCuentas(CuentaBancaria cuenta){ 
        cuenta.setTitular(this.getDni());
        this.cuentas.add(cuenta);
    }

=======

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


>>>>>>> ed4412701248b4cdb0fbea8b6abd4383865b38c2
    
}
