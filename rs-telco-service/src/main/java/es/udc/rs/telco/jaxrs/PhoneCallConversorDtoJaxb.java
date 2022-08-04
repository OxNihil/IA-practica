package es.udc.rs.telco.jaxrs;

import es.udc.rs.telco.jaxrs.dto.PhoneCallDtoJaxb;
import es.udc.rs.telco.model.phonecall.PhoneCall;

import java.util.ArrayList;
import java.util.List;

public class PhoneCallConversorDtoJaxb {
    public static PhoneCallDtoJaxb toPhoneCallDtoJaxb(PhoneCall c){
        return new PhoneCallDtoJaxb(c.getPhoneCallId(), c.getCustomerId(), c.getStartDate(),
                c.getDuration(),c.getDestinationNumber(),c.getPhoneCallType(),c.getPhoneCallStatus() );
    }

    public static List<PhoneCallDtoJaxb> toPhoneCallDtoJaxbList(List<PhoneCall> calls){
        List<PhoneCallDtoJaxb> phoneCallDtoList = new ArrayList<>();
        for(PhoneCall c: calls){
            phoneCallDtoList.add(toPhoneCallDtoJaxb(c));
        }
        return phoneCallDtoList;
    }
}
