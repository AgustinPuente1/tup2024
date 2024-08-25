package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CuentaBancaria {
    private long titular;  //DNI O CUIT
    private int idCuenta;
    private float saldo;
    private TipoCuenta tipoCuenta; // CUENTA_CORRIENTE, CAJA_AHORRO, CUENTA_INVERSIONES;
    private TipoMoneda moneda;
    private LocalDate fechaApertura;
    private List<MovimientosCuenta> movimientos;

    public CuentaBancaria(Long titular, TipoCuenta tipoCuenta, int idCuenta, TipoMoneda moneda, float saldo) {
        this.titular = titular;
        this.tipoCuenta = tipoCuenta;
        this.idCuenta = idCuenta;
        this.moneda = moneda;
        this.saldo = saldo;
        this.fechaApertura = obtenerFechaHoy();
        this.movimientos = new ArrayList<>();
    }

    public LocalDate obtenerFechaHoy(){
        return LocalDate.now();
    }

    




    
}
