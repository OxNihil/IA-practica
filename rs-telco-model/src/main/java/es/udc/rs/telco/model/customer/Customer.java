package es.udc.rs.telco.model.customer;

import java.time.LocalDateTime;
import java.util.Objects;

public class Customer {

    private Long customerId;
    private String name;
    private String dni;
    private String address;
    private LocalDateTime creationDate;
    private String phoneNumber;
    

	public Customer(String name, String dni, String address, String phoneNumber) {
		super();
		this.name = name;
		this.dni = dni;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public Customer(Customer customer) {
		this(customer.getName(), customer.getDni(), customer.getAddress(),customer.getPhoneNumber());
		this.setCreationDate(customer.getCreationDate());
		this.setCustomerId(customer.getCustomerId());
	}


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"customerId=" + customerId +
				", name='" + name + '\'' +
				", dni='" + dni + '\'' +
				", address='" + address + '\'' +
				", creationDate=" + creationDate +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return name.equals(customer.name) &&
				dni.equals(customer.dni) &&
				address.equals(customer.address) &&
				phoneNumber.equals(customer.phoneNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, dni, address, phoneNumber);
	}
}