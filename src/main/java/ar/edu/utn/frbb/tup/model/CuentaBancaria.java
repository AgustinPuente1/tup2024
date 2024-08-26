package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CuentaBancaria {
<<<<<<< HEAD
    private long titular;
    private long idCuenta;
    private float saldo;
    private TipoCuenta tipoCuenta; // CUENTA_CORRIENTE o CAJA_AHORRO
=======
    private long titular;  //DNI O CUIT
    private int idCuenta;
    private float saldo;
    private TipoCuenta tipoCuenta; // CUENTA_CORRIENTE, CAJA_AHORRO, CUENTA_INVERSIONES;
>>>>>>> ed4412701248b4cdb0fbea8b6abd4383865b38c2
    private TipoMoneda moneda;
    private LocalDate fechaApertura;
    private List<MovimientosCuenta> movimientos;

<<<<<<< HEAD
  
    public LocalDate obtenerFechaHoy(){
        return LocalDate.now();
    }

    public void setTitular(long dni) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTitular'");
=======
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
>>>>>>> ed4412701248b4cdb0fbea8b6abd4383865b38c2
    }

    




    
}
