package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.InstanceNotFoundExceptionDtoJaxb;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InstanceNotFoundExceptionMapper implements ExceptionMapper<InstanceNotFoundException> {
    @Override
    public Response toResponse(es.udc.ws.util.exceptions.InstanceNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(new InstanceNotFoundExceptionDtoJaxb(
                ex.getInstanceId(),ex.getMessage())).build();
    }
}
