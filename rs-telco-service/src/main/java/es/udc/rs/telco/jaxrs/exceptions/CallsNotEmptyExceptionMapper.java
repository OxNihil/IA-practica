package es.udc.rs.telco.jaxrs.exceptions;


import es.udc.rs.telco.jaxrs.dto.ExceptionsDtoJaxb;
import es.udc.rs.telco.model.exceptions.CallsNotEmptyException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CallsNotEmptyExceptionMapper implements ExceptionMapper<CallsNotEmptyException> {
    @Override
    public Response toResponse(CallsNotEmptyException ex) {
        return Response.status(Response.Status.CONFLICT).entity(new ExceptionsDtoJaxb("CallsNotEmpty", ex.getMessage())).build();
    }
}
