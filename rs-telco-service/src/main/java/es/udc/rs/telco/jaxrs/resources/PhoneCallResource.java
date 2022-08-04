package es.udc.rs.telco.jaxrs.resources;

import es.udc.rs.telco.jaxrs.PhoneCallConversorDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.PhoneCallDtoJaxb;
import es.udc.rs.telco.model.exceptions.InvalidCallStateException;
import es.udc.rs.telco.model.exceptions.MonthNotDoneException;
import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import es.udc.rs.telco.model.telcoservice.TelcoService;
import es.udc.rs.telco.model.telcoservice.TelcoServiceFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Path("/calls")
public class PhoneCallResource {
    public PhoneCallResource() {
    }
    private TelcoService telcoService = TelcoServiceFactory.getService();
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Operation(summary = "Adds a new PhoneCall",
            description = "A new Phonecall is added for an existing customer")
    @ApiResponse(responseCode = "200", description = "Added new PhoneCall")
    @ApiResponse(responseCode = "400", description = "Bad parameters",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "404", description = "The client Id does not exit",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))

    public Response addPhoneCall(PhoneCallDtoJaxb pcall, @Context UriInfo ui) throws  InputValidationException, InstanceNotFoundException {

        PhoneCall phonecall = telcoService.addPhoneCall(pcall.getCustomerId(), pcall.getStartDate(), pcall.getDuration(),
                pcall.getDestinationNumber(), pcall.getPhoneCallType());
        String phonecallIdStr = phonecall.getPhoneCallId().toString();
        PhoneCallDtoJaxb phonecalljaxb = PhoneCallConversorDtoJaxb.toPhoneCallDtoJaxb(phonecall);

        return Response.created(
                        URI.create(ui.getRequestUri().toString() + "/"
                                + phonecallIdStr))
                .entity(phonecalljaxb)
                .build();
    }

    @GET
    @Path("/callPending")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Operation(summary = "Returns phonecalls of a customer on a specific month",
            description = "Shows a list of all phonecalls made by a specific customer on the selected month of a year with status PENDING")
    @ApiResponse(responseCode = "200", description = "Ok")
    @ApiResponse(responseCode = "400", description = "Bad parameters",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "404", description = "The client Id does not exit",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
    @ApiResponse(responseCode = "409", description = "Wrong call state in the phonecalls",
            content = @Content(schema = @Schema(implementation = InvalidCallStateException.class)))
    @ApiResponse(responseCode = "409", description = "Month searched is not done",
            content = @Content(schema = @Schema(implementation = MonthNotDoneException.class)))
    public List<PhoneCallDtoJaxb> findPendingCalls (@QueryParam("id") String id, @QueryParam("year") String yearStr, @QueryParam("month") String monthStr)
                            throws InputValidationException, InstanceNotFoundException, InvalidCallStateException, MonthNotDoneException {
        Long customerId;
        Integer year, month;
        try{
            customerId = Long.valueOf(id);
            year = Integer.valueOf(yearStr);
            month = Integer.valueOf(monthStr);
        }catch (NumberFormatException e){
            throw new InputValidationException("Invalid request");
        }
        List<PhoneCall> phoneCallsPending = telcoService.findPhoneCallPending(customerId, year, month);
        List<PhoneCallDtoJaxb> calls = PhoneCallConversorDtoJaxb.toPhoneCallDtoJaxbList(phoneCallsPending);
        return calls;
    }

    @POST
    @Path("/updateStatus")
    @Operation(summary = "Updates the status of calls for the indicated month ",
            description = "Change the status from Pending to Billed and from Billed to Paid")
    @ApiResponse(responseCode = "204", description = "Update calls sucessfull")
    @ApiResponse(responseCode = "400", description = "Bad parameters",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "404", description = "Client Id could not be found",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
    @ApiResponse(responseCode = "409", description = "Month searched is not done",
            content = @Content(schema = @Schema(implementation = MonthNotDoneException.class)))
    public void updateStatus(
            @Parameter(description = "The customer Id") @NotNull @FormParam("customerId") String customerId,
            @Parameter(description = "The year to update") @NotNull @FormParam("year") String year,
            @Parameter(description = "The month to update") @NotNull @FormParam("month") String month)
            throws InputValidationException, InstanceNotFoundException, MonthNotDoneException {
        Integer iyear;
        Integer imonth;
        Long lcustomerId;
        PhoneCallStatus newPhoneCallStatus;
        try {
            lcustomerId = Long.valueOf(customerId);
            iyear = Integer.valueOf(year);
            imonth = Integer.valueOf(month);
        }catch(NumberFormatException e){
            throw new InputValidationException(e.getMessage());
        }
        telcoService.UpdatePhoneState(lcustomerId,iyear,imonth);
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Operation(summary = "search for calls in a time interval",
            description = "Returns the list of calls in the interval")
    @ApiResponse(responseCode = "200", description="Calls in the interval")
    @ApiResponse(responseCode = "400", description = "Bad parameters",
            content = @Content(schema = @Schema(implementation = InputValidationException.class)))
    @ApiResponse(responseCode = "404", description = "The client Id dosent exits",
            content = @Content(schema = @Schema(implementation = InstanceNotFoundException.class)))
    public Response findCallsByInterval(@QueryParam("customer") String customer,
                                        @QueryParam("startDate") String startdate,
                                        @QueryParam("endDate") String enddate,
                                        @DefaultValue("null") @QueryParam("type") String type,
                                        @DefaultValue("null") @QueryParam("start") String startind,
                                        @DefaultValue("null") @QueryParam("limit") String limit)
            throws InputValidationException, InstanceNotFoundException {
        Long customerId;
        LocalDateTime start;
        LocalDateTime end;
        try {
            customerId = Long.valueOf(customer);
        } catch (NumberFormatException e) {
            throw new InputValidationException("Failed to parse CustomerID");
        }
        try {
            start = (startdate.equals("null") ? null : LocalDateTime.parse(startdate));
            end = (enddate.equals("null") ? null : LocalDateTime.parse(enddate));
        }catch (DateTimeParseException e){
            throw new InputValidationException("Failed to parse startDate or endDate");
        }
        Integer istart,ilimit;
        PhoneCallType filterType;
        if (startind.equals("null")){ istart = null; }else{ istart = Integer.parseInt(startind);}
        if (limit.equals("null")){ ilimit = null; } else { ilimit = Integer.parseInt(limit);}
        filterType = strToPhoneCallType(type);
        List<PhoneCall> list = telcoService.findPhoneCallInterval(customerId,start,end, filterType,istart,ilimit);
        List<PhoneCallDtoJaxb> dtolist = PhoneCallConversorDtoJaxb.toPhoneCallDtoJaxbList(list);
        GenericEntity<List<PhoneCallDtoJaxb>> entity =
                new GenericEntity<>(dtolist) {};
        Response response = Response.ok(entity).build();
        return response;
    }

    private static PhoneCallType strToPhoneCallType(String type){
        switch (type.toUpperCase()){
            case "LOCAL":
                return PhoneCallType.LOCAL;
            case "NATIONAL":
                return PhoneCallType.NATIONAL;
            case "INTERNATIONAL":
                return PhoneCallType.INTERNATIONAL;
            default:
                return null;
        }
    }
}
