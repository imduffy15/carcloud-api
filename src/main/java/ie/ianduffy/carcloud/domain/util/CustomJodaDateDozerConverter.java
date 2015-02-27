package ie.ianduffy.carcloud.domain.util;

import org.dozer.DozerConverter;
import org.joda.time.DateTime;

public class CustomJodaDateDozerConverter extends DozerConverter<String, DateTime> {

    public CustomJodaDateDozerConverter() {
        super(String.class, DateTime.class);
    }

    @Override
    public String convertFrom(DateTime source, String destination) {
        return source.toString();
    }

    @Override
    public DateTime convertTo(String source, DateTime destination) {
        return DateTime.parse(source);
    }
}
