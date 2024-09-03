package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.List;

import ar.edu.utn.frbb.tup.controllers.dto.CuentaBancariaDto;

import java.util.ArrayList;

public class CuentaBancaria {
    private long titular; //Dto
    private long idCuenta;
    private float saldo; //Dto
    private TipoCuenta tipoCuenta; //Dto
    private TipoMoneda moneda; //Dto
    private LocalDate fechaApertura;
    private List<Transferencias> transferencias;
    private List<Transacciones> transacciones;

    public CuentaBancaria(CuentaBancariaDto cuentaBancariaDto) {
        this.titular = cuentaBancariaDto.getTitular();
        this.idCuenta = 0;
        this.saldo = cuentaBancariaDto.getSaldo();
        if (cuentaBancariaDto.getTipoCuenta() == "CC") {
            this.tipoCuenta = TipoCuenta.CUENTA_CORRIENTE;
        } else {
            this.tipoCuenta = TipoCuenta.CAJA_AHORRO;
        }
        if (cuentaBancariaDto.getMoneda() == "USD") {
            this.moneda = TipoMoneda.DOLARES;
        } else  {
            this.moneda = TipoMoneda.PESOS;
        }
        this.fechaApertura = LocalDate.now();
        this.transferencias = new ArrayList<>();
        this.transacciones = new ArrayList<>();
    }

    public long getTitular() {
        return titular;
    }
    public void setTitular(long titular) {
        this.titular = titular;
    }


    public long getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(long idCuenta) {
        this.idCuenta = idCuenta;
    }


    public float getSaldo() {
        return saldo;
    }
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    public void addDeposito(float monto) {
        this.saldo += monto;
    }
    public void addRetiro(float monto) {
        this.saldo -= monto;
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


    public LocalDate getFechaApertura() {
        return fechaApertura;
    }
    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }


    public List<Transferencias> getTransferencias() {
        return transferencias;
    }
    public void setTransferencias(List<Transferencias> transferencias) {
        this.transferencias = transferencias;
    }
    public void addTransferencia(Transferencias transferencia) {
        this.transferencias.add(transferencia);
    }
    
    
    public List<Transacciones> getTransacciones() {
        return transacciones;
    }
    public void setTransacciones(List<Transacciones> transacciones) {
        this.transacciones = transacciones;
    }
    public void addTransacciones(Transacciones transacciones) {
        this.transacciones.add(transacciones);
    }

    
}
