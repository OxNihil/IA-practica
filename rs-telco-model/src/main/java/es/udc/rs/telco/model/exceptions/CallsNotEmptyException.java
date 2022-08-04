package es.udc.rs.telco.model.exceptions;

public class CallsNotEmptyException extends Exception{

    private Long customerId;

    public CallsNotEmptyException(Long customerId){
        super("Customer with ID " + customerId + " has phonecalls and can´t be deleted");
        this.customerId = customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

}
