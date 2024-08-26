package ar.edu.utn.frbb.tup.model.exceptions;

public class ClienteNoExisteException extends Throwable {
    public ClienteNoExisteException(String message){
        super(message);
    }
}
