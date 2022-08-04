package es.udc.rs.telco.model.phonecall;

import java.time.LocalDateTime;
import java.util.Objects;

public class PhoneCall {

    private Long phoneCallId;
    private Long customerId;
    private LocalDateTime startDate;
    private Long duration;
    private String destinationNumber;
    private PhoneCallType phoneCallType;
    private PhoneCallStatus phoneCallStatus;
    
	public PhoneCall(Long clientId, LocalDateTime startDate, Long duration, String destinationNumber,
			PhoneCallType phoneCallType) {
		super();
		this.customerId = clientId;
		this.startDate = startDate;
		this.duration = duration;
		this.destinationNumber = destinationNumber;
		this.phoneCallType = phoneCallType;
	}

	public PhoneCall(PhoneCall call){
		this(call.getCustomerId(),call.getStartDate(),
				call.getDuration(),call.getDestinationNumber(),call.getPhoneCallType());
		this.setPhoneCallId(call.getPhoneCallId());
		this.setPhoneCallStatus(call.getPhoneCallStatus());
	}

	public Long getPhoneCallId() {
		return phoneCallId;
	}

	public void setPhoneCallId(Long phoneCallId) {
		this.phoneCallId = phoneCallId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
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

	public PhoneCallStatus getPhoneCallStatus() {
		return phoneCallStatus;
	}

	public void setPhoneCallStatus(PhoneCallStatus phoneCallStatus) {
		this.phoneCallStatus = phoneCallStatus;
	}

	@Override
	public String toString() {
		return "PhoneCall{" +
				"phoneCallId=" + phoneCallId +
				", customerId=" + customerId +
				", startDate=" + startDate +
				", duration=" + duration +
				", destinationNumber='" + destinationNumber + '\'' +
				", phoneCallType=" + phoneCallType +
				", phoneCallStatus=" + phoneCallStatus +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PhoneCall phoneCall = (PhoneCall) o;
		return phoneCallId.equals(phoneCall.phoneCallId) &&
				customerId.equals(phoneCall.customerId) &&
				startDate.equals(phoneCall.startDate) &&
				duration.equals(phoneCall.duration) &&
				destinationNumber.equals(phoneCall.destinationNumber) &&
				phoneCallType == phoneCall.phoneCallType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(phoneCallId, customerId, startDate, duration, destinationNumber, phoneCallType);
	}
}