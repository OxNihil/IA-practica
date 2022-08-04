package es.udc.rs.telco.jaxrs;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

public class LocalDateTimeToJaxbAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public String marshal(LocalDateTime time) throws Exception {
        return time.toString();
    }
    @Override
    public LocalDateTime unmarshal(String time) throws Exception {
        return LocalDateTime.parse(time);
    }
}
