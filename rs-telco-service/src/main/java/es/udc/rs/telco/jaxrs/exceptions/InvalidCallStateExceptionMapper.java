package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.ExceptionsDtoJaxb;
import es.udc.rs.telco.model.exceptions.InvalidCallStateException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidCallStateExceptionMapper implements ExceptionMapper<InvalidCallStateException> {

    @Override
    public Response toResponse(InvalidCallStateException ex) {
        return Response.status(Response.Status.CONFLICT).entity(new ExceptionsDtoJaxb("InvalidCallState", ex.getMessage())).build();

    }
}
