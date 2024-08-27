package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.List;

import org.apache.tomcat.jni.Library;

import java.util.ArrayList;

import ar.edu.utn.frbb.tup.controllers.Dto.CuentaBancariaDto;

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
        this.tipoCuenta = cuentaBancariaDto.getTipoCuenta();
        this.moneda = cuentaBancariaDto.getMoneda();
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
