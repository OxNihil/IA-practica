package es.udc.rs.telco.client.service.rest.exceptions;

public class ClientInvalidCallStateException extends Exception {

    public ClientInvalidCallStateException(String exMsg){
        super(exMsg);
    }
}