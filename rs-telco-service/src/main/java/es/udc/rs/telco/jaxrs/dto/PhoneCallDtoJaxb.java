package es.udc.rs.telco.jaxrs.dto;

import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import jakarta.xml.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement(name="phoneCall")
@XmlType(name = "CallsJaxb", propOrder = {"customerId","startDate","duration","destinationNumber",
        "phoneCallType","phoneCallStatus"})
public class PhoneCallDtoJaxb {
    @XmlAttribute(name="phoneCallId", required = false)
    private Long phoneCallId;

    @XmlElement(required = true)
    private Long customerId;

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    private LocalDateTime startDate;

    @XmlElement(required = true)
    private Long duration;

    @XmlElement(required = true)
    private String destinationNumber;

    @XmlElement(required = true)
    private PhoneCallType phoneCallType;

    @XmlElement(required = true)
    private PhoneCallStatus phoneCallStatus;

    public PhoneCallDtoJaxb(){}

    public PhoneCallDtoJaxb(Long phoneCallId, Long customerId, LocalDateTime startDate,
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
                "phoneCallId="+phoneCallId+
                ", customerId=" + customerId +
                ", startDate=" + startDate +
                ", duration=" + duration +
                ", destinationNumber='" + destinationNumber + "'" +
                ", phoneCallType=" + phoneCallType +
                ", phoneCallStatus=" + phoneCallStatus + "}";
    }

    public PhoneCall toPhoneCall(PhoneCallDtoJaxb c){
        return new PhoneCall(
                c.getCustomerId(), c.getStartDate(), c.getDuration(), c.getDestinationNumber(), c.getPhoneCallType()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneCallDtoJaxb that = (PhoneCallDtoJaxb) o;
        return phoneCallId.equals(that.phoneCallId) && customerId.equals(that.customerId) && startDate.equals(that.startDate) && duration.equals(that.duration) && destinationNumber.equals(that.destinationNumber) && phoneCallType == that.phoneCallType && phoneCallStatus == that.phoneCallStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneCallId, customerId, startDate, duration, destinationNumber, phoneCallType, phoneCallStatus);
    }
}
