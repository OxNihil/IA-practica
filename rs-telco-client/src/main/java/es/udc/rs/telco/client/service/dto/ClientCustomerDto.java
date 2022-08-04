package es.udc.rs.telco.client.service.dto;

import es.udc.rs.telco.client.service.rest.dto.CustomerJaxb;
import es.udc.rs.telco.client.service.rest.dto.ObjectFactory;
import jakarta.xml.bind.JAXBElement;

public class ClientCustomerDto {

    private Long customerId;
    private String name;
    private String dni;
    private String address;
    private String phoneNumber;


    public ClientCustomerDto(Long customerId, String name, String dni, String address, String phoneNumber) {
        this.customerId = customerId;
        this.name = name;
        this.dni = dni;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public Long getcustomerId() {
        return customerId;
    }

    public void setcustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public JAXBElement<CustomerJaxb> toJaxbCustomer(){
        CustomerJaxb customer = new CustomerJaxb();
        customer.setCustomerId(this.getcustomerId() != null ? customer.getCustomerId() : -1);
        customer.setName(this.getName());
        customer.setAddress(this.getAddress());
        customer.setDni(this.getDni());
        customer.setPhoneNumber(this.getPhoneNumber());
        JAXBElement<CustomerJaxb> jaxbElement = new ObjectFactory().createCustomer(customer);
        return jaxbElement;
    }


    @Override
    public String toString() {
        return "ClientCustomerDto{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
