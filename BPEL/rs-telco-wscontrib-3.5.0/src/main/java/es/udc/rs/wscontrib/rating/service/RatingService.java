package es.udc.rs.wscontrib.rating.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import es.udc.rs.wscontrib.billing.service.PricedPhoneCall;
import jakarta.annotation.PostConstruct;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;

@WebService(name = "RatingProvider", serviceName = "RatingProviderService", targetNamespace = "http://rs.udc.es/rating")
public class RatingService {

	private final static double localPrice = 0.001;
	private final static double nationalPrice = 0.005;
	private final static double internationalPrice = 0.05;

	public RatingService() {
	}

	@PostConstruct()
	private void init() {

	}

	@WebMethod(operationName = "getDiscount")
	public double getDiscount(
			@XmlElement(required = true) @WebParam(name = "customerId") Long customerId,
			@XmlElement(required = true) @WebParam(name = "month") short month,
			@XmlElement(required = true) @WebParam(name = "year") short year)
			throws RatingException {

		System.out.println("getDiscount\n  input parameters"
				+ "\n    customerId: " + customerId + "\n    month: " + month
				+ "\n    year: " + year);

		double discount = 0;
		if (customerId % 2 == 0) {
			discount = customerId % 10 + 1;
		} else {
			System.out.println("  error: " + "Customer " + customerId
					+ " has not discount the month " + month + "/" + year);
			throw new RatingException("Customer " + customerId
					+ " has not discount the month " + month + "/" + year);
		}

		System.out.println("  return value" + "\n    " + discount);

		return discount;

	}

	@WebMethod(operationName = "getPhoneCallPrice")
	public double getPhoneCallPrice(@WebParam(name = "phoneCall")PhoneCall phoneCall)
			throws RatingException {

		System.out.println("getPhoneCallPrice\n  input parameters"
				+ "\n    phoneCall: " + phoneCall);

		double price = 0;
		PhoneCallType type = phoneCall.getPhoneCallType();
		if (type == null) {
			System.out.println("  error: " + "Unrecognized type "
					+ phoneCall.getPhoneCallType() + " in call with id "
					+ phoneCall.getPhoneCallId());
			throw new RatingException("Unrecognized type "
					+ phoneCall.getPhoneCallType() + " in call with id "
					+ phoneCall.getPhoneCallId());
		} else {
			switch (type) {
			case LOCAL:
				price = phoneCall.getDuration() * localPrice;
				break;
			case NATIONAL:
				price = phoneCall.getDuration() * nationalPrice;
				break;
			case INTERNATIONAL:
				price = phoneCall.getDuration() * internationalPrice;
				break;
			default:
				System.out.println("  error: " + "Unrecognized type "
						+ phoneCall.getPhoneCallType() + " in call with id "
						+ phoneCall.getPhoneCallId());
				throw new RatingException("Unrecognized type "
						+ phoneCall.getPhoneCallType() + " in call with id "
						+ phoneCall.getPhoneCallId());
			}
		}
		BigDecimal bdPrice = new BigDecimal(price);
		bdPrice = bdPrice.setScale(2, RoundingMode.HALF_UP);

		System.out.println("  return value" + "\n    " + bdPrice);

		return bdPrice.doubleValue();
	}

}
