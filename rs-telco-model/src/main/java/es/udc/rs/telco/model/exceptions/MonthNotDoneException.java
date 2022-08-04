package es.udc.rs.telco.model.exceptions;

public class MonthNotDoneException extends Exception{
    public MonthNotDoneException(String errorMessage) {
        super(errorMessage);
    }
}