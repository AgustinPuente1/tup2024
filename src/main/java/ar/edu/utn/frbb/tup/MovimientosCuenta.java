package tup2024.src.main.java.ar.edu.utn.frbb.tup;

import java.time.LocalDateTime;

public class MovimientosCuenta {
    private String tipoOperacion; //descripcion de la operacion
    private float monto;
    private LocalDateTime fechaHora;
    
    public MovimientosCuenta(String tipoOperacion, float monto, LocalDateTime fechaHora) {
        this.tipoOperacion = tipoOperacion;
        this.monto = monto;
        this.fechaHora = fechaHora;
    }

    

    public String getTipoOperacion() {
        return tipoOperacion;
    }
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public float getMonto() {
        return monto;
    }
    public void setMonto(float monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Método para registrar un depósito en una cuenta corriente
    public static void registrarDepositoCorriente(CuentaBancaria cuentaBanco, float monto) {
        //Aca van a ir cosas más especificas para cada uno de los depositos o retiros
        //Por ejemplo, verificar que el monto del depósito no exceda un límite máximo y cosas así 
        if (monto > 0) {
            LocalDateTime fechaHora = LocalDateTime.now();
            MovimientosCuenta movimiento = new MovimientosCuenta("Depósito en cuenta corriente", monto, fechaHora);
            cuentaBanco.agregarMovimiento(movimiento);
        } else {
            throw new IllegalArgumentException("El monto del depósito debe ser mayor que cero.");
        }
    }
    
    // Método para registrar un retiro en una cuenta corriente
    public static void registrarRetiroCorriente(CuentaBancaria cuentaBanco, float monto) {
        // 
        //
        if (cuentaBanco.getSaldo() <= monto){
            throw new IllegalArgumentException("El monto a Retirar debe ser menor o igual al saldo de la cuenta");
        }
        if (monto > 0) {
            LocalDateTime fechaHora = LocalDateTime.now();
            MovimientosCuenta movimiento = new MovimientosCuenta("Retiro en cuenta corriente", -monto, fechaHora);
            cuentaBanco.agregarMovimiento(movimiento);
        } else {
            throw new IllegalArgumentException("El monto del retiro debe ser mayor que cero.");
        }
    }
    
    // Método para registrar un depósito en una cuenta de ahorros
    public static void registrarDepositoAhorros(CuentaBancaria cuentaBanco, float monto) {
        // 
        // 
        if (monto > 0) {
            LocalDateTime fechaHora = LocalDateTime.now();
            MovimientosCuenta movimiento = new MovimientosCuenta("Depósito en cuenta de ahorros", monto, fechaHora);
            cuentaBanco.agregarMovimiento(movimiento);
        } else {
            throw new IllegalArgumentException("El monto del depósito debe ser mayor que cero.");
        }
    }
    
    // Método para registrar un retiro en una cuenta de ahorros
    public static void registrarRetiroAhorros(CuentaBancaria cuentaBanco, float monto) {
        // 
        // 
        if (cuentaBanco.getSaldo() <= monto){
            throw new IllegalArgumentException("El monto a Retirar debe ser menor o igual al saldo de la cuenta");
        }
        if (monto > 0) {
            LocalDateTime fechaHora = LocalDateTime.now();
            MovimientosCuenta movimiento = new MovimientosCuenta("Retiro en cuenta de ahorros", -monto, fechaHora);
            cuentaBanco.agregarMovimiento(movimiento);
        } else {
            throw new IllegalArgumentException("El monto del retiro debe ser mayor que cero.");
        }
    }
}
