package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CuentaBancaria {
    private long titular;
    private long idCuenta;
    private float saldo;
    private TipoCuenta tipoCuenta; // CUENTA_CORRIENTE o CAJA_AHORRO
    private TipoMoneda moneda;
    private LocalDate fechaApertura;
    private List<MovimientosCuenta> movimientos;

  
    public LocalDate obtenerFechaHoy(){
        return LocalDate.now();
    }

    public void setTitular(long dni) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTitular'");
    }

    




    
}
