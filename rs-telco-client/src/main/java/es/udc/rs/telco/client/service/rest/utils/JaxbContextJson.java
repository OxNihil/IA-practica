package es.udc.rs.telco.client.service.rest.utils;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.JAXBElement;

@Provider
public class JaxbContextJson implements ContextResolver<ObjectMapper> {
    private ObjectMapper mapper = new ObjectMapper();

    public JaxbContextJson() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JaxbAnnotationModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.addMixIn(JAXBElement.class, JAXBElementMixin.class);
    }


    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        return mapper;
    }
}

