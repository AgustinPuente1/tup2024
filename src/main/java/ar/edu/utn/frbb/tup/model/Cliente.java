package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ar.edu.utn.frbb.tup.controllers.dto.ClienteDto;


public class Cliente extends Persona{
    private TipoCliente tipo; //Dto
    private String banco;
    private LocalDate fechaAlta;
    private Set<CuentaBancaria> cuentas;

    //Contructor para uso común
    public Cliente(ClienteDto clienteDto){
        super(clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getDni(), clienteDto.getFechaNacimiento(), clienteDto.getMail(), clienteDto.getTelefono());
        if (clienteDto.getTipo().equalsIgnoreCase("F") || clienteDto.getTipo().equalsIgnoreCase("FISICA")){
            this.tipo = TipoCliente.PERSONA_FISICA;
        }else{
            this.tipo = TipoCliente.PERSONA_JURIDICA;
        }
        this.banco = "Banco TUP";
        this.fechaAlta = LocalDate.now();
        this.cuentas = new HashSet<>();
    }

    //Contructores para testeos
    public Cliente(){
        super();
    }

    public Cliente(String nombre, String apellido, long dni, LocalDate fechaNacimiento, String mail, String telefono, String tipo){
        super(nombre, apellido, dni, fechaNacimiento, mail, telefono);
        if (tipo.equalsIgnoreCase("F") || tipo.equalsIgnoreCase("FISICA")){
            this.tipo = TipoCliente.PERSONA_FISICA;
        }else{
            this.tipo = TipoCliente.PERSONA_JURIDICA;
        }
        this.banco = "Banco TUP";
        this.fechaAlta = LocalDate.now();
        this.cuentas = new HashSet<>();
    }

    public TipoCliente getTipo() {
        return tipo;
    }
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
        this.cuentas.add(cuenta);
    }
    public void deleteCuenta(CuentaBancaria cuenta){ 
        for (CuentaBancaria c : this.cuentas){
            if (c.getIdCuenta() == cuenta.getIdCuenta()){
                this.cuentas.remove(c);
            }
        }
    }

    
}
