package es.udc.rs.telco.client.service;

import es.udc.rs.telco.client.service.dto.ClientCustomerDto;
import es.udc.rs.telco.client.service.dto.ClientPhoneCallDto;
import es.udc.rs.telco.client.service.rest.exceptions.ClientCallsNotEmptyException;
import es.udc.rs.telco.client.service.rest.exceptions.ClientMonthNotDoneException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.List;

public interface ClientTelcoService {

    Long addClient(ClientCustomerDto customer) throws InputValidationException;

    Long addPhoneCall(ClientPhoneCallDto phoneCall) throws InputValidationException, InstanceNotFoundException;

    void deleteClient(Long customerId) throws InputValidationException, InstanceNotFoundException, ClientCallsNotEmptyException, ClientCallsNotEmptyException;

    List<ClientPhoneCallDto> findPhoneCallInterval(Long id, String startDate, String endDate,
                                                   String type,Integer startInd, Integer endInd)
            throws InstanceNotFoundException, InputValidationException;

    void updateStatus(Long customerId, int year, int month)
            throws InstanceNotFoundException, InputValidationException, ClientMonthNotDoneException;

}
