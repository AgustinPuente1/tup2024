package ar.edu.utn.frbb.tup.model;

import org.springframework.stereotype.Component;

@Component
public class Banco {
    //limite de saldo negativo
    private static final float LIMITE_SOBREGIRO = -100000;

    //% comision transferencia
    private static final float COMISION_TRANSFER_ARS = 0.02f;
    private static final float COMISION_TRANSFER_USD = 0.005f;

    // a partir de cuando se cobra comision
    private static final float LIMITE_COMISION_ARS = 1000000;
    private static final float LIMITE_COMISION_USD = 5000;

    //Mensajes de recibo
    private static final String EXITO_ENTRE_BANCOS = ("Transferencia entre bancos diferentes exitosa");
    private static final String EXITO_EN_BANCO = ("Transferencia entre cuentas del mismo banco exitosa");
    private static final String MENSAJE_ERROR = ("Error del sisteama al realizar la transferencia, intente de nuevo");
    private static final String ERROR_SALDO_INSUFICIENTE = ("No tiene suficiente saldo");

    public float getLimiteSobregiro() {
        return LIMITE_SOBREGIRO;
    }

    public float getComisionTransferArs() {
        return COMISION_TRANSFER_ARS;
    }

    public float getComisionTransferUsd() {
        return COMISION_TRANSFER_USD;
    }

    public float getLimiteComisionArs() {
        return LIMITE_COMISION_ARS;
    }

    public float getLimiteComisionUsd() {
        return LIMITE_COMISION_USD;
    }

    public String getExitoEntreBancos() {
        return EXITO_ENTRE_BANCOS;
    }

    public String getExitoEnBanco() {
        return EXITO_EN_BANCO;
    }

    public String getMensajeError() {
        return MENSAJE_ERROR;
    }

    public String getSaldoInsuficiente(){
        return ERROR_SALDO_INSUFICIENTE;
    }
}
