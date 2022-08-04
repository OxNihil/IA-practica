package es.udc.rs.telco.model.telcoservice;

import es.udc.rs.telco.model.exceptions.CallsNotEmptyException;
import es.udc.rs.telco.model.exceptions.MonthNotDoneException;
import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.rs.telco.model.exceptions.InvalidCallStateException;
import es.udc.rs.telco.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;

public interface TelcoService {

    //CU-01 Dar de alta cliente

    Customer addCustomer(String Name, String dni, String address, String phoneNumber) throws InputValidationException;

    //CU-02 Eliminar cliente

    void delCustomer(Long customerId) throws InstanceNotFoundException, CallsNotEmptyException, CallsNotEmptyException;

    //CU-03 Modificar cliente

    void updateCustomer(Long customerId, String name, String dni, String address) throws InstanceNotFoundException, InputValidationException;

    //CU-04 Consultar datos de cliente a partir de DNI/identificador

    Customer findClientByDni(String dni) throws InstanceNotFoundException;

    Customer findClientById(Long id) throws InstanceNotFoundException;

    //CU-05 Consultar datos de clientes a partir de cadena de texto
    List<Customer> findClientByKeywords(String keyword, Integer start, Integer size);

    //CU-06 Añadir llamada telefónica

    PhoneCall addPhoneCall(Long customerId, LocalDateTime startDate, long duration,
                                  String destinationNumber, PhoneCallType type) throws  InstanceNotFoundException, InputValidationException;

    //CU-07 Obtener llamadas de cliente en un mes que ya haya transcurrido

    List<PhoneCall> findPhoneCallPending(Long customerId, int year, int month) throws InputValidationException,
                    InstanceNotFoundException, InvalidCallStateException, MonthNotDoneException;

    //CU-08 Cambio de estado de las llamadas una vez transcurrido el mes (PENDING -> BILLED, BILLED -> PAID)
    void UpdatePhoneState(Long customerId, int year, int month)
            throws InstanceNotFoundException, InputValidationException, MonthNotDoneException;

    //CU-09 Obtener datos de llamadas telefónicas de un cliente durante un intervalo de tiempo

    List<PhoneCall> findPhoneCallInterval(Long CustomerId, LocalDateTime start, LocalDateTime end,
                                                 PhoneCallType type, Integer startInd ,
                                                 Integer endInd) throws InstanceNotFoundException ;

}
