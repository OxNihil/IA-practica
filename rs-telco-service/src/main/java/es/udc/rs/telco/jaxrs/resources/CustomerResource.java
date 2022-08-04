package es.udc.rs.telco.jaxrs.resources;

import es.udc.rs.telco.jaxrs.CustomerConversorDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.telco.model.customer.Customer;
import es.udc.rs.telco.model.exceptions.CallsNotEmptyException;
import es.udc.rs.telco.model.telcoservice.TelcoService;
import es.udc.rs.telco.model.telcoservice.TelcoServiceFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;


@Path("clients")
public class CustomerResource {
        public CustomerResource(){}

        private TelcoService telcoService = TelcoServiceFactory.getService();

        @POST
        @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Operation(summary = "Adds a new Customer",
                description = "A new Customer is added")
        @ApiResponse(responseCode = "201", description = "Added new Customer")
        @ApiResponse(responseCode = "400", description = "Bad parameters",
                content = @Content(schema = @Schema(implementation = InputValidationException.class)))

        public Response addCustomer(CustomerDtoJaxb cust, @Context UriInfo ui) throws  InputValidationException {

                Customer customer = telcoService.addCustomer(cust.getName(), cust.getDni(), cust.getAddress(), cust.getPhoneNumber());
                String customerIdStr = customer.getCustomerId().toString();
                CustomerDtoJaxb custjaxb = CustomerConversorDtoJaxb.toCustomerDtoJaxb(customer);

                return Response.created(
                                URI.create(ui.getRequestUri().toString() + "/"
                                        + customerIdStr))
                        .entity(custjaxb)
                        .build();
        }


        @DELETE
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Path("/{id : \\d+}")
        @Operation(summary = "Removes a Customer",
                description = "A Customer is removed")
        @ApiResponse(responseCode = "200", description = "Customer Removed")
        @ApiResponse(responseCode = "404", description = "Id could not be found",
                content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
        public void deleteCustomer(@PathParam("id") String id) throws InstanceNotFoundException, CallsNotEmptyException{

                telcoService.delCustomer(Long.valueOf(id));
        }



        @PUT
        @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        @Path("/{id : \\d+}")
        @Operation(summary = "Update customer that matches the id",
                description = "Change parameters of the customer with that id")
        @ApiResponse(responseCode = "204", description = "Update Customer sucessfull")
        @ApiResponse(responseCode = "400", description = "Bad parameters",
                content = @Content(schema = @Schema(implementation = InputValidationException.class)))
        @ApiResponse(responseCode = "404", description = "Customer Id could not be found",
                content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
        public void updateCustomer(CustomerDtoJaxb cust, @PathParam("id") String id) throws InputValidationException, InstanceNotFoundException {

                telcoService.updateCustomer(Long.valueOf(id), cust.getName(),cust.getDni(), cust.getAddress());
        }





        @GET
        @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
        @Operation(summary = "Find customers whose names match the keyword",
                description = "Returns the list of clients that match the keyword")
        @ApiResponse(responseCode = "200", description = "customers whose names match the keyword")
        public Response findClientByKeywords(
                @Parameter(description = "Keywords to use in the search", required = true)
                @DefaultValue("") @QueryParam("keyword") String keyword,
                @Parameter(description = "Client list index start", required = false)
                @DefaultValue("null") @QueryParam("start") String start,
                @Parameter(description = "Client list index end", required = false)
                @DefaultValue("null") @QueryParam("size") String size){
                Integer istart,isize;
                if (start.equals("null")){ istart = null; } else{ istart = Integer.parseInt(start);}
                if (size.equals("null")){ isize = null; } else { isize = Integer.parseInt(size);}
                List<Customer> list = telcoService.findClientByKeywords(keyword,istart,isize);
                List<CustomerDtoJaxb> dtolist = CustomerConversorDtoJaxb.toCustomerDtoJaxbList(list);
                GenericEntity<List<CustomerDtoJaxb>> entity =
                        new GenericEntity<>(dtolist) {};
                Response response = Response.ok(entity).build();
                return response;
        }

        @GET
        @Path("/dni/{dni}")
        @Operation(summary = "Find customer that matches the dni",
                description = "Returns the client that matches the dni")
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
        @ApiResponse(responseCode = "200", description = "Client found by DNI")
        @ApiResponse(responseCode = "404", description = "DNI could not be found",
                content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
        public CustomerDtoJaxb findClientByDni(@PathParam("dni") String dni) throws InstanceNotFoundException {
                return CustomerConversorDtoJaxb.toCustomerDtoJaxb(TelcoServiceFactory.getService().findClientByDni(dni));
        }

        @GET
        @Path("/{id : \\d+}")
        @Operation(summary = "Find customer that matches the id",
                description = "Returns the client that matches the id")
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
        @ApiResponse(responseCode = "200", description = "Client found by Id")
        @ApiResponse(responseCode = "404", description = "Id could not be found",
                content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
        public CustomerDtoJaxb findClientById(@PathParam("id") String id) throws InstanceNotFoundException {
                return CustomerConversorDtoJaxb.toCustomerDtoJaxb(TelcoServiceFactory.getService().findClientById(Long.valueOf(id)));
        }
}
