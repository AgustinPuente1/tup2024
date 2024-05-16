package tup2024.src.main.java.ar.edu.utn.frbb.tup;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CuentaBancaria {
    private Cliente cliente;
    private int idCuenta;
    private float saldo;
    private String tipoCuenta; //"corriente" o "ahorros"
    private LocalDate fechaApertura;
    private List<MovimientosCuenta> movimientos;

    public CuentaBancaria(Cliente cliente, int idCuenta, float saldo, String tipoCuenta, LocalDate fechaApertura) {
        // vadlida que saldo no sea menor a 0
        if (!validarSaldo(saldo)){
            throw new IllegalArgumentException("El saldo no puede ser menor a 0");
        }

        // valida que el tipo de cuenta sea corriente o ahorros
        if (!validarTipoCuenta(tipoCuenta)){
            throw new IllegalArgumentException("El tipo de cuenta tiene que ser 'corriente' o 'ahorros'");
        }

        // valida que la fecha de apertura no sea en el futoro
        LocalDate fechaActual = LocalDate.now();
        if (fechaApertura.isAfter(fechaActual)){
            throw new IllegalArgumentException("La fecha de apertura tiene que ser en el pasado");
        }

        this.cliente = cliente;
        this.idCuenta = idCuenta;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
        this.fechaApertura = fechaApertura;
        this.movimientos = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public float getSaldo() {
        return saldo;
    }
    public void setSaldo(float saldo) {
        if (!validarSaldo(saldo)){
            throw new IllegalArgumentException("El saldo no puede ser menor a 0");
        }
        this.saldo = saldo;
    }
    private boolean validarSaldo(float saldo) {
        return saldo >= 0;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(String tipoCuenta) {
        if (!validarTipoCuenta(tipoCuenta)){
            throw new IllegalArgumentException("El tipo de cuenta tiene que ser 'corriente' o 'ahorros'");
        }
        this.tipoCuenta = tipoCuenta;
    }
    private boolean validarTipoCuenta(String tipoCuenta) {
        return tipoCuenta.equalsIgnoreCase("corriente") || tipoCuenta.equalsIgnoreCase("ahorros");
    }


    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void agregarMovimiento(MovimientosCuenta movimiento) {
        movimientos.add(movimiento);
    }
    public List<MovimientosCuenta> obtenerMovimientos() {
        return movimientos;
    }

    @Override
    public String toString() {
        return "CuentaBancaria [cliente=" + cliente + ", idCuenta=" + idCuenta + ", saldo=" + saldo + ", tipoCuenta="
                + tipoCuenta + ", fechaApertura=" + fechaApertura + ", movimientos=" + obtenerMovimientos() + "]";
    }
}
