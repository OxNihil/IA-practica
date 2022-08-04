package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.client.service.rest.dto.CallsJaxb;
import es.udc.rs.telco.client.service.rest.dto.CustomerJaxb;
import es.udc.rs.telco.client.service.rest.dto.InputValidationExceptionJaxb;
import es.udc.rs.telco.client.service.rest.dto.InstanceNotFoundExceptionJaxb;
import es.udc.rs.telco.client.service.rest.exceptions.*;
import es.udc.rs.telco.client.service.rest.utils.JaxbContextJson;
import es.udc.rs.telco.client.service.rest.utils.toJaxbException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.*;
import es.udc.rs.telco.client.service.rest.dto.*;

import es.udc.rs.telco.client.service.dto.ClientCustomerDto;
import es.udc.rs.telco.client.service.dto.ClientPhoneCallDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;


import es.udc.rs.telco.client.service.ClientTelcoService;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.util.ArrayList;
import java.util.List;

public abstract class RestClientTelcoService implements ClientTelcoService {

	private static jakarta.ws.rs.client.Client client = null;

	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientTelcoService.endpointAddress";
	private WebTarget endPointWebTarget = null;

	/*
	 * Client instances are expensive resources. It is recommended a configured
	 * instance is reused for the creation of Web resources. The creation of Web
	 * resources, the building of requests and receiving of responses are
	 * guaranteed to be thread safe. Thus a Client instance and WebTarget
	 * instances may be shared between multiple threads.
	 */
	private static Client getClient() {
		if (client == null) {
			client = ClientBuilder.newClient();
			client.register(JaxbContextJson.class);
			client.register(JacksonFeature.class);
		}
		return client;
	}

	private WebTarget getEndpointWebTarget() {
		if (endPointWebTarget == null) {
			endPointWebTarget = getClient()
					.target(ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER));
		}
		return endPointWebTarget;
	}

	protected abstract MediaType getMediaType();


	@Override
	public Long addClient(ClientCustomerDto customer) throws InputValidationException {
		WebTarget wt = getEndpointWebTarget().path("clients");
		Response response = wt.request().accept(this.getMediaType())
				.post(Entity.entity(customer.toJaxbCustomer(), this.getMediaType()));
		try {
			validateResponse(Response.Status.CREATED.getStatusCode(), response);
			CustomerJaxb newCustomer = response.readEntity(CustomerJaxb.class);
			return newCustomer.getCustomerId();
		} catch (InputValidationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	@Override
	public Long addPhoneCall(ClientPhoneCallDto phoneCall) throws InputValidationException, InstanceNotFoundException {
		Response response = null;
		try {
			response = getEndpointWebTarget().path("calls")
					.request().post(Entity.entity(phoneCall.toJaxbCall(), this.getMediaType()));
			validateResponse(Response.Status.CREATED.getStatusCode(), response);
			CallsJaxb newCall = response.readEntity(CallsJaxb.class);
			return newCall.getPhoneCallId();
		} catch (InstanceNotFoundException | InputValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	@Override
	public void deleteClient(Long customerId) throws InstanceNotFoundException, ClientCallsNotEmptyException {
		WebTarget wt = getEndpointWebTarget().path("clients/{id}").resolveTemplate("id", customerId);
		Response response = wt.request().accept(this.getMediaType()).delete();
		try {
			validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
		} catch (InstanceNotFoundException ex) {
			throw ex;
		}catch (ClientCallsNotEmptyException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (response != null) {
				response.close();
			}
		}

	}

	@Override
	public List<ClientPhoneCallDto> findPhoneCallInterval(Long id, String startDate, String endDate, String type,
														  Integer startInd, Integer endInd)
			throws InstanceNotFoundException, InputValidationException {
		Response response = null;
		List<ClientPhoneCallDto> results = new ArrayList<>();
		try {
			if (type == null && startInd != null && endInd != null) {
				response = getEndpointWebTarget().path("calls").queryParam("customer", id.toString())
						.queryParam("startDate", startDate).queryParam("endDate", endDate)
						.queryParam("start", startInd)
						.queryParam("limit", endInd)
						.request().accept(this.getMediaType()).get();
			} else if (type == null && startInd == null && endInd == null) {
				response = getEndpointWebTarget().path("calls").queryParam("customer", id.toString())
						.queryParam("startDate", startDate).queryParam("endDate", endDate)
						.request().accept(this.getMediaType()).get();
			} else if (type != null && startInd == null & endInd == null){
				response = getEndpointWebTarget().path("calls").queryParam("customer", id.toString())
						.queryParam("startDate", startDate).queryParam("endDate", endDate)
						.queryParam("type", type)
						.request().accept(this.getMediaType()).get();
			} else {
				response = getEndpointWebTarget().path("calls").queryParam("customer", id.toString())
						.queryParam("startDate", startDate).queryParam("endDate", endDate)
						.queryParam("type", type)
						.queryParam("start", startInd)
						.queryParam("limit", endInd)
						.request().accept(this.getMediaType()).get();
			}
			validateResponse(Response.Status.OK.getStatusCode(), response);
			List<CallsJaxb> lista = response.readEntity(new GenericType<List<CallsJaxb>>() {
			});
			for (CallsJaxb l : lista) {
				ClientPhoneCallDto call = new ClientPhoneCallDto(l.getPhoneCallId(), l.getCustomerId(),
						l.getStartDate(), l.getDuration(), l.getDestinationNumber(),
						l.getPhoneCallType(), l.getPhoneCallStatus());
				results.add(call);
			}
			return results;
		} catch (InputValidationException | InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (response != null) {response.close();}
		}
	}


	@Override
	public void updateStatus(Long customerId, int year, int month)
			throws InstanceNotFoundException, InputValidationException,ClientMonthNotDoneException {
		Response response = null;
		Form form = new Form().param("customerId", customerId.toString())
				.param("year", String.valueOf(year)).param("month", String.valueOf(month));
		try {
			response = getEndpointWebTarget().path("calls").path("updateStatus")
					.request().post(Entity.form(form));
			validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
		} catch (InstanceNotFoundException | InputValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (response != null) { response.close(); }
		}
	}

	private void validateResponse(int expectedCode, Response response) throws InputValidationException, InstanceNotFoundException,
			ClientMonthNotDoneException, ClientInvalidCallStateException, ClientCallsNotEmptyException {
		Response.Status statusCode = Response.Status.fromStatusCode(response.getStatus());
		if (expectedCode == statusCode.getStatusCode() &&
				(response.getStatus() == Response.Status.NO_CONTENT.getStatusCode())) {
			return;
		}
		String contentType = response.getMediaType() != null ? response.getMediaType().toString() : null;
		if (expectedCode == statusCode.getStatusCode() && this.getMediaType().toString().equalsIgnoreCase(contentType)) {
			return;
		}
		if (expectedCode == statusCode.getStatusCode() && !this.getMediaType().toString().equalsIgnoreCase(contentType)) {
			throw new RuntimeException("HTTP error; status code = " + statusCode);
		}
		if (expectedCode != statusCode.getStatusCode()) {
			switch (statusCode) {
				case BAD_REQUEST: {
					InputValidationExceptionJaxb exDto = response.readEntity(InputValidationExceptionJaxb.class);
					throw toJaxbException.toInputValidationException(exDto);
				}
				case NOT_FOUND: {
					InstanceNotFoundExceptionJaxb exDto = response.readEntity(InstanceNotFoundExceptionJaxb.class);
					throw toJaxbException.toInstanceNotFoundException(exDto);
				}
				case CONFLICT: {
					CustomExceptionJaxb exDto = response.readEntity(CustomExceptionJaxb.class);
					if (exDto.getErrorType().equals("MonthNotDone")) {
						throw new ClientMonthNotDoneException(exDto.getMessage());
					} else if (exDto.getErrorType().equals("InvalidCallState")) {
						throw new ClientInvalidCallStateException(exDto.getMessage());
					} else if (exDto.getErrorType().equals("CallsNotEmpty")) {
						throw new ClientCallsNotEmptyException(exDto.getMessage());
					} else {
						throw new RuntimeException("HTTP error; status code = " + statusCode);
					}
				}
				default:
						throw new RuntimeException("HTTP error; status code = " + statusCode);
			}
		}
	}


}
