package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.*;



@XmlRootElement(name="inputValidationException")
@XmlType(name="inputValidationExceptionJaxb")
public class InputValidationExceptionDtoJaxb {

    @XmlElement(required = true)
    protected String message;

    public InputValidationExceptionDtoJaxb(){};
    public String getMessage() {
        return message;
    }

    /**
     * Define el valor de la propiedad message.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Obtiene el valor de la propiedad errorType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */

    @Override
    public String toString(){
        return "InputValidationExceptionDtoJaxb{" +
                "message='" + message + "'}";
    }
}
