package es.udc.rs.wscontrib.billing.service;

import es.udc.rs.wscontrib.rating.service.PhoneCallType;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PricedPhoneCall {

	private Long phoneCallId;
	private Long customerId;
	private Calendar startDate;
	private Long duration;
	private String destinationNumber;
	private PhoneCallType phoneCallType;
	private double price;

	public PricedPhoneCall() {
	}

	public PricedPhoneCall(Long phoneCallId, Calendar startDate, Long duration,
						   String destinationNumber, PhoneCallType phoneCallType, double price) {
		super();
		this.phoneCallId = phoneCallId;
		this.startDate = startDate;
		this.duration = duration;
		this.destinationNumber = destinationNumber;
		this.phoneCallType = phoneCallType;
		this.price = price;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public PhoneCallType getPhoneCallType() {
		return phoneCallType;
	}

	public void setPhoneCallType(PhoneCallType phoneCallType) {
		this.phoneCallType = phoneCallType;
	}

	public Long getPhoneCallId() {
		return phoneCallId;
	}

	public void setPhoneCallId(Long phoneCallId) {
		this.phoneCallId = phoneCallId;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getDestinationNumber() {
		return destinationNumber;
	}

	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
	}

	public PhoneCallType getType() {
		return phoneCallType;
	}

	public void setType(PhoneCallType type) {
		this.phoneCallType = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "PricedPhoneCall{" +
				"phoneCallId=" + phoneCallId +
				", customerId=" + customerId +
				", startDate=" + (startDate!=null?startDate.getTime():startDate)  +
				", duration=" + duration +
				", destinationNumber='" + destinationNumber + '\'' +
				", phoneCallType=" + phoneCallType +
				", price=" + price +
				'}';
	}
}

