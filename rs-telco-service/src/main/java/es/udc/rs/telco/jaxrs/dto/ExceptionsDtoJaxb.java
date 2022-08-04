package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "customException")
@XmlType(name = "customExceptionJaxb")
public class ExceptionsDtoJaxb {
    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private String message;

    public ExceptionsDtoJaxb() {}
    public ExceptionsDtoJaxb(String errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "errorType='" + errorType + '\'' +
                ", message='" + message + '\'' + '}';
    }
}





