package es.udc.rs.telco.jaxrs.dto;

import es.udc.rs.telco.model.customer.Customer;
import jakarta.xml.bind.annotation.*;

import java.util.Objects;


@XmlRootElement(name = "customer")
@XmlType(name = "customerJaxb", propOrder = {"name","dni","address","phoneNumber"})
public class CustomerDtoJaxb {
    @XmlAttribute(name = "customerId", required = false)
    private Long customerId;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String dni;
    @XmlElement(required = true)
    private String address;
    @XmlElement()
    private String phoneNumber;

    public CustomerDtoJaxb(){}

    public CustomerDtoJaxb(Long customerId, String name, String dni, String address, String phoneNumber){
        this.customerId = customerId;
        this.name = name;
        this.dni = dni;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getCustomerId(){ return customerId; }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerDtoJaxb{" +
                "customerId=" + customerId +
                ", name='" + name + "'" +
                ", dni='" + dni + "'" +
                ", address='" + address + "'" +
                ", phoneNumber='" + phoneNumber + "'}";
    }

    public Customer toCustomer() {
        return new Customer(this.name, this.dni, this.address, this.phoneNumber);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDtoJaxb that = (CustomerDtoJaxb) o;
        return customerId.equals(that.customerId) && Objects.equals(name, that.name) && dni.equals(that.dni)
                && Objects.equals(address, that.address) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, dni, address, phoneNumber);
    }
}
