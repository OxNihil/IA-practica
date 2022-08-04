package es.udc.rs.telco.model.exceptions;

public class InvalidCallStateException extends Exception{
    public InvalidCallStateException(String errorMessage) {
        super(errorMessage);
    }
}
