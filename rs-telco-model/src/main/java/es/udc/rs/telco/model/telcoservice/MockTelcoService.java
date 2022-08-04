package es.udc.rs.telco.model.telcoservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.udc.rs.telco.model.customer.Customer;
import es.udc.rs.telco.model.phonecall.PhoneCall;

import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.rs.telco.model.exceptions.InvalidCallStateException;
import es.udc.rs.telco.model.exceptions.MonthNotDoneException;
import es.udc.rs.telco.model.exceptions.CallsNotEmptyException;
import es.udc.ws.util.validation.PropertyValidator;


public class MockTelcoService implements TelcoService {

	private static Map<Long, Customer> clientsMap = new LinkedHashMap<Long, Customer>();
	private static Map<Long,PhoneCall> phoneCallsMap = new LinkedHashMap<Long,PhoneCall>();
	private static Map<Long,List<PhoneCall>> phoneCallsByUserMap = new LinkedHashMap<Long,List<PhoneCall>>();


	private static long lastClientId = 0;
	private static long lastPhoneCallId = 0;


	private static synchronized long getNextClientId() {
		return ++lastClientId;
	}

	private static synchronized long getNextPhoneCallId() {
		return ++lastPhoneCallId;
	}


	@Override
	public Customer addCustomer(String Name, String dni, String address, String phoneNumber)
			throws InputValidationException{
		Customer c = new Customer(Name, dni, address, phoneNumber);
		validateCustomer(c);
		try {
			String num = phoneNumber;
			for (Customer cus : clientsMap.values()) {
				if (num.equals(cus.getPhoneNumber())){
					throw new InputValidationException("The phone number already exists!");
				}
			}
			c.setCustomerId(this.getNextClientId());
			c.setCreationDate(java.time.LocalDateTime.now());
			this.clientsMap.put(c.getCustomerId(), new Customer(c));
		} finally {
			return c;
		}
	}

	@Override
	public void delCustomer(Long customerId) throws InstanceNotFoundException, CallsNotEmptyException {
		Customer cli = clientsMap.get(customerId);
		if (cli == null) {
			throw new InstanceNotFoundException(customerId, "Customer does not exist!");
		}
		for (PhoneCall call : phoneCallsMap.values()) {
			if (call.getCustomerId() == customerId) {
				throw new CallsNotEmptyException(customerId);
			}
			clientsMap.remove(customerId);
		}
	}

	@Override
	public void updateCustomer(Long customerId, String name, String dni, String address)
			throws InstanceNotFoundException, InputValidationException {
		Customer c2 = clientsMap.get(customerId);
		validateCustomer(c2);
		if (c2 == null) {
			throw new InstanceNotFoundException(customerId,
					Customer.class.getName());
		}
		c2.setName(name);
		c2.setDni(dni);
		c2.setAddress(address);
	}

	@Override
	public Customer findClientByDni(String dni) throws InstanceNotFoundException {
		for (Customer c : clientsMap.values()) {
			if (dni.equals(c.getDni())) {
				return c;
			}
		}
		throw new InstanceNotFoundException(dni,"No client found!");
	}

	@Override
	public Customer findClientById(Long id) throws InstanceNotFoundException {
		Customer c = clientsMap.get(id);
		if (c == null){
			throw new InstanceNotFoundException(id, Customer.class.getName());
		}
		return c;
	}

	@Override
	public List<Customer> findClientByKeywords(String keyword, Integer start, Integer size) {
		List<Customer> foundCustomers = new ArrayList<Customer>();
		String searchKeyword = keyword != null ? keyword.toLowerCase() : "";
		for (Customer c : clientsMap.values()) {
			if (c.getName().toLowerCase().contains(searchKeyword)) {
				foundCustomers.add(new Customer(c));
			}
		}
		return SubList(foundCustomers,start,size);
	}


	@Override
	public PhoneCall addPhoneCall(Long customerId, LocalDateTime startDate, long duration, String destinationNumber,
								  PhoneCallType type) throws InstanceNotFoundException, InputValidationException {
        findClientById(customerId);
		PhoneCall pcall = new PhoneCall(customerId, startDate, duration, destinationNumber, type);
		validatePhoneCall(pcall);
		pcall.setPhoneCallId(getNextPhoneCallId());
		pcall.setPhoneCallStatus(PhoneCallStatus.PENDING);
		phoneCallsMap.put(pcall.getPhoneCallId(), pcall);
		if (!phoneCallsByUserMap.containsKey(customerId)){
			List<PhoneCall> lista = new ArrayList<>();
			lista.add(pcall);
			phoneCallsByUserMap.put(customerId,lista);
		} else {
			List<PhoneCall> lista = phoneCallsByUserMap.get(customerId);
			lista.add(pcall);
			phoneCallsByUserMap.put(customerId,lista);
		}
		return new PhoneCall(pcall);
	}

	@Override
	public List<PhoneCall> findPhoneCallPending(Long customerId, int year, int month) throws InputValidationException,
					InstanceNotFoundException, InvalidCallStateException, MonthNotDoneException {
		findClientById(customerId);
		if (year <  0 || month > 11 || month < 0){
			throw new InputValidationException("Month or year are invalid");
		}
		if (LocalDateTime.now().isBefore(LocalDateTime.of(year, month, 1, 0, 0).plusMonths(1))){
			throw new MonthNotDoneException("Month still has not passed yet");
		}
		List<PhoneCall> pcall_list = phoneCallsByUserMap.get(customerId);
		List<PhoneCall> pcalls = new ArrayList<>();
		if (pcall_list == null){
			return pcalls;
		}
		for (PhoneCall pcall : pcall_list) {
			if ((pcall.getStartDate().getYear() == year) && (pcall.getStartDate().getMonthValue() == month)){
				if (pcall.getPhoneCallStatus() != PhoneCallStatus.PENDING){
					throw new InvalidCallStateException("Call State is not PENDING");
				}
				pcalls.add(pcall);
			}
		}
		return pcalls;
	}

	@Override
	public void UpdatePhoneState(Long customerId, int year, int month)
			throws InstanceNotFoundException, InputValidationException,MonthNotDoneException {
		if (year <  0 || month > 11 || month < 0){
			throw new InputValidationException("Month or year are invalid");
		}
		if (LocalDateTime.now().isBefore(LocalDateTime.of(year, month, 1, 0, 0).plusMonths(1))){
			throw new MonthNotDoneException("Month still has not passed yet");
		}
		LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
		LocalDateTime end =  LocalDateTime.of(year, month, 1, 0, 0).plusMonths(1);
		List<PhoneCall> calls = findPhoneCallInterval(customerId,start,end,null,null,null);
		for ( PhoneCall call: calls){
			int index = calls.indexOf(call);
			Long callid = call.getPhoneCallId();
			Long customerid = phoneCallsMap.get(callid).getCustomerId();
			if (call.getPhoneCallStatus() == PhoneCallStatus.PENDING) {
				call.setPhoneCallStatus(PhoneCallStatus.BILLED);
			} else if (call.getPhoneCallStatus() == PhoneCallStatus.BILLED) {
				call.setPhoneCallStatus(PhoneCallStatus.PAID);
			}
		}
	}

	@Override
	public List<PhoneCall> findPhoneCallInterval(Long customerId, LocalDateTime start, LocalDateTime end,
												 PhoneCallType type, Integer startInd, Integer endInd) throws InstanceNotFoundException {
		List<PhoneCall> results = new ArrayList<>();
		if (start.isAfter(end)){
			return results;
		}
		if (!phoneCallsByUserMap.containsKey(customerId)){
			throw new InstanceNotFoundException(customerId,"The customer dont exits");
		}
		for (PhoneCall call: phoneCallsByUserMap.get(customerId)) {
			if (call.getStartDate().isAfter(start) && call.getStartDate().isBefore(end)){
				if (type == null){
					results.add(call);
				} else if (call.getPhoneCallType() == type){
					results.add(call);
				}
			}
		}
		return SubList(results,startInd,endInd);
	}

	public static <T> List<T> SubList(List<T> list, Integer fromIndex, Integer toIndex) {
		int size = list.size();
		fromIndex = (fromIndex == null ? 0 : fromIndex);
		toIndex = (toIndex == null ? (size - fromIndex) : toIndex);
		if (fromIndex >= size || toIndex <= 0) {
			return new ArrayList<T>();
		}
		fromIndex = Math.max(0, fromIndex);
		toIndex = Math.min(size, toIndex+fromIndex);
		return list.subList(fromIndex, toIndex);
	}

	private static void validatePhoneCall(PhoneCall call) throws InputValidationException {
		if (call == null) {
			throw new InputValidationException("Phone call can't be null.");
		}
		PropertyValidator.validateLong("CustomerId", call.getCustomerId(),0,99999999);
		PropertyValidator.validateLong("duration", call.getDuration(),0,999999999);
		PropertyValidator.validateMandatoryString("DestinationNumber", call.getDestinationNumber());
	}

	//Only for test
	public void delCalls() {
		phoneCallsMap.clear();
	}

	private static void validateCustomer(Customer c)
			throws InputValidationException {
		if (c == null) {
			throw new InputValidationException("Costumer can't be null.");
		}
		PropertyValidator.validateMandatoryString("name", c.getName());
		PropertyValidator.validateMandatoryString("address",
				c.getAddress());
		PropertyValidator.validateMandatoryString("dni",
				c.getDni());
		PropertyValidator.validateMandatoryString("phonenumber",
				c.getPhoneNumber());
	}



}
