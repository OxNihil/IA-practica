package es.udc.rs.wscontrib.rating.service;

import java.util.Calendar;

public class PhoneCall {

	private Long phoneCallId;
	private Calendar startDate;
	private Long duration;
	private String destinationNumber;
	private PhoneCallType phoneCallType;

	public PhoneCall() {
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

	public PhoneCallType getPhoneCallType() {
		return phoneCallType;
	}

	public void setPhoneCallType(PhoneCallType phoneCallType) {
		this.phoneCallType = phoneCallType;
	}

	@Override
	public String toString() {
		return "PhoneCall{" +
				"phoneCallId=" + phoneCallId +
				", startDate=" + (startDate!=null?startDate.getTime():startDate) +
				", duration=" + duration +
				", destinationNumber='" + destinationNumber + '\'' +
				", phoneCallType=" + phoneCallType +
				'}';
	}

}
