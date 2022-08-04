package es.udc.rs.telco.jaxrs.dto;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="InstanceNotFoundException")
@XmlType(name="InstanceNotFoundExceptionJaxb")
public class InstanceNotFoundExceptionDtoJaxb {
    @XmlElement(required = true)
    private String message;
    @XmlElement(required = true)
    private Object instance;

    public InstanceNotFoundExceptionDtoJaxb(){};

    public  InstanceNotFoundExceptionDtoJaxb(Object instance, String message){
        this.setInstance(instance);
        this.setMessage(message);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {this.message =  message;}

    public Object getInstance() { return instance; }

    public void setInstance(Object instance) {this.instance = instance;}

    @Override
    public String toString(){
        return "InstanceNotFoundExceptionDtoJaxb{" +
                "message='" + message + "'"+
                "instance='" + instance.toString() +"'"+
                "}";
    }
}
