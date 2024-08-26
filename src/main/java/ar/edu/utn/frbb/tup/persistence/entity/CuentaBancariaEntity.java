package ar.edu.utn.frbb.tup.persistence.entity;

import java.util.HashMap;
import java.util.Map;

import ar.edu.utn.frbb.tup.model.CuentaBancaria;

public class CuentaBancariaEntity {
    private static int id = 0;
    protected static Map<Integer, CuentaBancaria> cuentasBancarias =  new HashMap<>();

    public static void save(CuentaBancaria cuentaBancaria){
        id += 1;
        cuentasBancarias.put(id, cuentaBancaria);
    }

    public static Map<Integer, CuentaBancaria> dataBase(){
        return cuentasBancarias;
    }
}
