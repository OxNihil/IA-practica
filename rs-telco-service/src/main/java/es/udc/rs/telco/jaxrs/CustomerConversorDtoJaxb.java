package es.udc.rs.telco.jaxrs;

import es.udc.rs.telco.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.telco.model.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerConversorDtoJaxb {
    public static CustomerDtoJaxb toCustomerDtoJaxb(Customer c){
        return new CustomerDtoJaxb(c.getCustomerId(), c.getName(), c.getDni(), c.getAddress(),
                c.getPhoneNumber());
    }


    public static List<CustomerDtoJaxb> toCustomerDtoJaxbList(List<Customer> customers){
        List<CustomerDtoJaxb> customerDtoList = new ArrayList<>();
        for(Customer c: customers){
            customerDtoList.add(toCustomerDtoJaxb(c));
        }
        return customerDtoList;
    }

}
