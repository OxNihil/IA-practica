package es.udc.rs.wscontrib.billing.service;

import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(name = "BillingProvider", serviceName = "BillingProviderService", targetNamespace = "http://rs.udc.es/billing")
public class BillingService {

	private final static double basePrice = 15;
	private static long billIdCount = 1;

	public BillingService() {
	}

	@PostConstruct()
	private void init() {

	}

	@WebMethod(operationName = "createAndSendBill")
	public BillSummary createAndSendBill(
			@WebParam(name = "customerId") Long customerId,
			@WebParam(name = "month") short month,
			@WebParam(name = "year") short year,
			@WebParam(name = "priced-call") List<PricedPhoneCall> phoneCalls,
			@WebParam(name = "discount") double discount) {

		System.out.println("createAndSendBill\n  input parameters"
				+ "\n    customerId: " + customerId + "\n    month: " + month
				+ "\n    year: " + year + "\n    pricedCalls: " + phoneCalls
				+ "\n    discount:" + discount);

		double billAmount = basePrice - discount;
		for (PricedPhoneCall phoneCall : phoneCalls) {
			billAmount += phoneCall.getPrice();
		}
		BillSummary billSummary = new BillSummary(billIdCount++, billAmount);

		System.out.println("  return value" + "\n    " + billSummary);

		return billSummary;
	}
}
