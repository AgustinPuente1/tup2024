package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.persistence.CuentaBancariaDao;

public class CuentaBancariaService {
    public static void agregarCuentaBancaria(CuentaBancaria cuentaBancaria) throws CuentaAlreadyExistsException{
        if (CuentaBancariaDao.existCuenta(cuentaBancaria)){
            throw new CuentaAlreadyExistsException("La cuenta que quiere agregar ya existe");
        }

        CuentaBancariaDao.saveCuenta(cuentaBancaria);
    }

    public static void agregarMovimiento(){

    }

    public static CuentaBancaria buscarCuentaBancariaXID(int id) throws CuentaNoExisteException{
        CuentaBancaria cuentaBancaria = CuentaBancariaDao.findXID(id);
        if (cuentaBancaria == null){
            throw new CuentaNoExisteException("La cuenta que se busca no existe");
        } else {
            return cuentaBancaria;
        }
        
    }
}
