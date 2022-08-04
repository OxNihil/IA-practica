package es.udc.rs.wscontrib.telco.service;

import jakarta.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "TelcoException", targetNamespace = "http://rs.udc.es/telco")
public class TelcoException extends Exception {

    private String message;

    public TelcoException(String message) {
        this.message = message;
    }

    public String getFaultInfo() {
        return message;
    }

}
