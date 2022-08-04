package es.udc.rs.telco.jaxrs.exceptions;


import es.udc.ws.util.exceptions.InputValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InputValidationExceptionMapper implements ExceptionMapper<InputValidationException> {
    @Override
    public Response toResponse(InputValidationException ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
    }
}
