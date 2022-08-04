@XmlSchema(namespace = "http://rs.udc.es/calls/xml", elementFormDefault = XmlNsForm.QUALIFIED)
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type= LocalDateTime.class, value= LocalDateTimeToJaxbAdapter.class)})
@XmlAccessorType(XmlAccessType.FIELD)
package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import java.time.LocalDateTime;
import es.udc.rs.telco.jaxrs.LocalDateTimeToJaxbAdapter;
