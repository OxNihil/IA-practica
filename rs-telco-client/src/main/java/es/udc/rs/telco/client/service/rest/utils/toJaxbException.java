package es.udc.rs.telco.client.service.rest.utils;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.rs.telco.client.service.rest.dto.InputValidationExceptionJaxb;
import es.udc.rs.telco.client.service.rest.dto.InstanceNotFoundExceptionJaxb;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class toJaxbException {
        public static InputValidationException toInputValidationException(
                InputValidationExceptionJaxb exDto) {
            return new InputValidationException(exDto.getMessage());
        }

      public static InstanceNotFoundException toInstanceNotFoundException(
            InstanceNotFoundExceptionJaxb exDto) {
        return new InstanceNotFoundException(exDto.getInstance(),exDto.getMessage());
    }
}
