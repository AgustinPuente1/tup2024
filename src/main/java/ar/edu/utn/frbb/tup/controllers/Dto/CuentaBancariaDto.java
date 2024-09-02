package ar.edu.utn.frbb.tup.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CuentaBancariaDto {
    @NotNull(message = "El DNI del titular no puede ser nulo")
    private long titular;
    @NotNull(message = "El saldo inicial no puede ser nulo")
    private float saldo;
    @NotBlank(message = "El tipo de cuenta no puede ser nulo (CC o CA)")
    private String tipoCuenta;
    @NotBlank(message = "La moneda no puede ser nula (USD o ARS)")
    private String moneda;

    public long getTitular(){
        return titular;
    }
    public void setTitular(long dni) {
        this.titular = dni;
    }

    public float getSaldo() {
        return saldo;
    }
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }


    public String getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }


    public String getMoneda() {
        return moneda;
    }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
