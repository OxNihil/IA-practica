package es.udc.rs.telco.client.service.rest.exceptions;

public class ClientCallsNotEmptyException extends Exception {
    public ClientCallsNotEmptyException(String exMsg){
        super(exMsg);
    }
}