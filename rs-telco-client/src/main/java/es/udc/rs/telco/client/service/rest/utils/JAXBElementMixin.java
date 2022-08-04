package es.udc.rs.telco.client.service.rest.utils;
import com.fasterxml.jackson.annotation.JsonValue;

public interface JAXBElementMixin<T> {
    @JsonValue
    Object getValue();
}
