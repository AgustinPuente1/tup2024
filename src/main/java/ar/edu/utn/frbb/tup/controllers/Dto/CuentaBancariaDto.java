package ar.edu.utn.frbb.tup.controllers.Dto;

import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;

public class CuentaBancariaDto {
    private long titular;
    private float saldo;
    private TipoCuenta tipoCuenta;
    private TipoMoneda moneda;

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


    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }


    public TipoMoneda getMoneda() {
        return moneda;
    }
    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }
}
