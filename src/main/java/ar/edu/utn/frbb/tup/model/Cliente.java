package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ar.edu.utn.frbb.tup.controllers.Dto.ClienteDto;


public class Cliente extends Persona{
    private TipoCliente tipo; //Dto
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

    
}
