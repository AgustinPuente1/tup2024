package ar.edu.utn.frbb.tup.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frbb.tup.controllers.dto.TransaccionesDto;
import ar.edu.utn.frbb.tup.model.Banco;
import ar.edu.utn.frbb.tup.model.CuentaBancaria;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.TipoTransaccion;
import ar.edu.utn.frbb.tup.model.Transacciones;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.CuentaNoExisteException;
import ar.edu.utn.frbb.tup.model.exceptions.MonedaNoCoincideException;
import ar.edu.utn.frbb.tup.model.exceptions.MontoNoValidoException;
import ar.edu.utn.frbb.tup.persistence.CuentaBancariaDao;
import ar.edu.utn.frbb.tup.persistence.TransaccionesDao;
import ar.edu.utn.frbb.tup.service.TransaccionesService;

@Service
public class TransaccionesServiceImp implements TransaccionesService {

    private TransaccionesDao transaccionesDao;
    private CuentaBancariaDao cuentaBancariaDao;
    private Banco banco;

    @Autowired
    public TransaccionesServiceImp(TransaccionesDao transaccionesDao, CuentaBancariaDao cuentaBancariaDao, Banco banco) {
        this.transaccionesDao = transaccionesDao;
        this.cuentaBancariaDao = cuentaBancariaDao;
        this.banco = banco;
    }

    @Override
    public List<Transacciones> obtenerAllTransacciones(){
        return transaccionesDao.getAllTransacciones();
    }

    /**
     * Crea una nueva transacci n en la base de datos.
     * 
     * @param transaccionesDto datos de la transacci n a crear
     * @return la transacci n creada
     * @throws MontoNoValidoException              si el monto es negativo o 0
     * @throws CuentaNoExisteException             si la cuenta no existe
     * @throws MonedaNoCoincideException           si la moneda de la cuenta no es en pesos
     * @throws ClienteNoExisteException            si el cliente no existe
     */
    @Override
    public Transacciones crearTransaccion(TransaccionesDto transaccionesDto) throws MontoNoValidoException, CuentaNoExisteException, MonedaNoCoincideException, ClienteNoExisteException{
        //Excepciones iniciales
        if (transaccionesDto.getMonto() <= 0) {
            throw new MontoNoValidoException("El monto no puede ser negativo o 0");
        }

        CuentaBancaria cuentaOrigen = cuentaBancariaDao.getCuentaBancariaById(transaccionesDto.getIdCuenta());
        Transacciones transaccion = new Transacciones(transaccionesDto);

        //Excepciones monto y que se cuenta en pesos
        if (!cuentaOrigen.getMoneda().equals(TipoMoneda.PESOS)) {
            throw new MonedaNoCoincideException("La cuenta no es en pesos");
        } else if (transaccion.getTipo().equals(TipoTransaccion.CREDITO) 
            && (cuentaOrigen.getSaldo() - transaccionesDto.getMonto()) < banco.getLimiteSobregiro()) {
            throw new MontoNoValidoException("El saldo sobrepasa el limite de sobregiro");
        }

        return transaccionesDao.createTransaccion(transaccion);
    }

    @Override
    public void borrarAllTransacciones(){
        transaccionesDao.deleteTransacciones();
    }
}
