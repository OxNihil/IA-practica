package es.udc.rs.telco.client.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import es.udc.rs.telco.client.service.rest.dto.*;
import jakarta.xml.bind.JAXBElement;

public class ClientPhoneCallDto {

    private Long phoneCallId;
    private Long customerId;
    private LocalDateTime startDate;
    private Long duration;
    private String destinationNumber;
    private PhoneCallType phoneCallType;
    private PhoneCallStatus phoneCallStatus;

    public ClientPhoneCallDto(Long phoneCallId, Long customerId, LocalDateTime startDate,
                            Long duration, String destinationNumber,
                            PhoneCallType phoneCallType, PhoneCallStatus phoneCallStatus){
        this.customerId = customerId;
        this.phoneCallId = phoneCallId;
        this.startDate = startDate;
        this.duration = duration;
        this.destinationNumber = destinationNumber;
        this.phoneCallType = phoneCallType;
        this.phoneCallStatus = phoneCallStatus;
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

    public PhoneCallStatus getPhoneCallStatus() {
        return phoneCallStatus;
    }

    public void setPhoneCallStatus(PhoneCallStatus phoneCallStatus) {
        this.phoneCallStatus = phoneCallStatus;
    }

    public PhoneCallType getPhoneCallType() {
        return phoneCallType;
    }

    public void setPhoneCallType(PhoneCallType phoneCallType) {
        this.phoneCallType = phoneCallType;
    }

    @Override
    public String toString() {
        return "PhoneCallDtoJaxb{" +
                "phoneCallId=" + phoneCallId +
                ", customerId=" + customerId +
                ", startDate=" + startDate +
                ", duration=" + duration +
                ", destinationNumber='" + destinationNumber + "'" +
                ", phoneCallType=" + phoneCallType +
                ", phoneCallStatus=" + phoneCallStatus + "}";
    }

    public JAXBElement<CallsJaxb> toJaxbCall(){
        CallsJaxb call = new CallsJaxb();
        call.setPhoneCallId(this.getPhoneCallId() != null ? call.getCustomerId() : -1);
        call.setCustomerId(this.getCustomerId());
        call.setStartDate(this.getStartDate());
        call.setDuration(this.getDuration());
        call.setDestinationNumber(this.getDestinationNumber());
        call.setPhoneCallStatus(this.getPhoneCallStatus());
        call.setPhoneCallType(this.getPhoneCallType());
        return new ObjectFactory().createPhoneCall(call);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientPhoneCallDto that = (ClientPhoneCallDto) o;
        return phoneCallId.equals(that.phoneCallId) && customerId.equals(that.customerId) && startDate.equals(that.startDate)
                && duration.equals(that.duration) && destinationNumber.equals(that.destinationNumber)
                && phoneCallType == that.phoneCallType && phoneCallStatus == that.phoneCallStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneCallId, customerId, startDate, duration, destinationNumber, phoneCallType, phoneCallStatus);
    }


}
