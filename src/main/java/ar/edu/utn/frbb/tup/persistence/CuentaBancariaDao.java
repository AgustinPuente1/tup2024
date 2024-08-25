package ar.edu.utn.frbb.tup.persistence;

import java.util.Map;

import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.persistence.entity.CuentaBancariaEntity;

public class CuentaBancariaDao {
    public static void saveCuenta(CuentaBancaria cuentaBancaria){
        CuentaBancariaEntity.save(cuentaBancaria);
    }

    public static CuentaBancaria findXID(int id){
        Map<Integer, CuentaBancaria> cuentasBancariasDataBase = CuentaBancariaEntity.dataBase();
        if (cuentasBancariasDataBase.containsKey(id)){
            return cuentasBancariasDataBase.get(id);
        }
        return null;
    }
    public static Map<Integer, CuentaBancaria> findCuentas(){
        return CuentaBancariaEntity.dataBase();
    }

    public static boolean existCuenta(CuentaBancaria cuentaBancaria){
        Map<Integer, CuentaBancaria> cuentasBancariasDataBase = findCuentas();
        if (cuentasBancariasDataBase.containsValue(cuentaBancaria)){
            return true;
        } else {
            return false;
        }
    }
    public static boolean existCuentaBancariaXID(int id){
        Map<Integer, CuentaBancaria> cuentasBancariasDataBase = findCuentas();
        if (cuentasBancariasDataBase.containsKey(id)){
            return true;
        } else {
            return false;
        }
    }
}
