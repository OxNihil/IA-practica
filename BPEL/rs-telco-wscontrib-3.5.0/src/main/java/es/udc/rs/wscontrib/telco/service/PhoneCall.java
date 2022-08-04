package es.udc.rs.wscontrib.telco.service;

import java.util.Calendar;

public class PhoneCall {

    private Long phoneCallId;
    private Long customerId;
    private Calendar startDate;
    private Long duration;
    private String destinationNumber;
    private PhoneCallType phoneCallType;
    private PhoneCallStatus phoneCallStatus;

    public PhoneCall(Long phoneCallId, Long customerId, Calendar startDate, Long duration, String destinationNumber,
                     PhoneCallType phoneCallType) {
        super();
        this.phoneCallId = phoneCallId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.duration = duration;
        this.destinationNumber = destinationNumber;
        this.phoneCallType = phoneCallType;
        this.phoneCallStatus = PhoneCallStatus.PENDING;
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
                ", startDate=" + (startDate!=null?startDate.getTime():startDate)  +
                ", duration=" + duration +
                ", destinationNumber='" + destinationNumber + '\'' +
                ", phoneCallType=" + phoneCallType +
                ", phoneCallStatus=" + phoneCallStatus +
                '}';
    }

}
