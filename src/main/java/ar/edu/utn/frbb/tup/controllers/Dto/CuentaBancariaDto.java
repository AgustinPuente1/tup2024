package ar.edu.utn.frbb.tup.controllers.dto;

public class CuentaBancariaDto {
    private long titular;
    private float saldo;
    private String tipoCuenta;
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
