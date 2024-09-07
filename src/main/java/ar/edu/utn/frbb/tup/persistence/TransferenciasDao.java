package ar.edu.utn.frbb.tup.persistence;

import java.util.List;

import ar.edu.utn.frbb.tup.model.Transferencias;
import ar.edu.utn.frbb.tup.model.exceptions.ClienteNoExisteException;


public interface TransferenciasDao {
    List<Transferencias> getAllTransfers();

    void transferBetweenBanks(Transferencias transferencia) throws ClienteNoExisteException;

    void transferInBank(Transferencias transferencia) throws ClienteNoExisteException;

    void deleteTransfers();
}
