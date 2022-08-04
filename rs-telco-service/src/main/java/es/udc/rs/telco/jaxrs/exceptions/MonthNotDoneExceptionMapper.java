package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.ExceptionsDtoJaxb;
import es.udc.rs.telco.model.exceptions.MonthNotDoneException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MonthNotDoneExceptionMapper implements ExceptionMapper<MonthNotDoneException> {

    @Override
    public Response toResponse(MonthNotDoneException ex) {
        return Response.status(Response.Status.CONFLICT).entity(new ExceptionsDtoJaxb("MonthNotDone", ex.getMessage())).build();
    }
}