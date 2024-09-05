package ar.edu.utn.frbb.tup.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Banco {
    private List<Cliente> clientes;
    private List<CuentaBancaria> cuentas;
    private List<Transacciones> transacciones;
    private List<Transferencias> transferencias;

    //limite de saldo negativo
    private static final float LIMITE_SOBREGIRO = -100000;

    public static final float COMISION_TRANSFER_ARS = 0.02f;

    public float getLimiteSobregiro() {
        return LIMITE_SOBREGIRO;
    }
}
