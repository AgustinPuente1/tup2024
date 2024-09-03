package ar.edu.utn.frbb.tup.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Banco {
    private List<Cliente> clientes;
    private List<CuentaBancaria> cuentas;
    private List<Transacciones> transacciones;
    private List<Transferencias> transferencias;

    private static final float limiteSobregiro = 100000;

    public float getLimiteSobregiro() {
        return limiteSobregiro;
    }
}
