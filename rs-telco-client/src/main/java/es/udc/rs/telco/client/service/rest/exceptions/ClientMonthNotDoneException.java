package es.udc.rs.telco.client.service.rest.exceptions;

public class ClientMonthNotDoneException extends Exception {
    public ClientMonthNotDoneException(String exMsg){
        super(exMsg);
    }
}