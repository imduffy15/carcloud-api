package ie.ianduffy.carcloud.domain.util;

import org.dozer.DozerConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class CustomJodaDateDozerConverter extends DozerConverter<String, DateTime> {

    public CustomJodaDateDozerConverter() {
        super(String.class, DateTime.class);
    }

    @Override
    public DateTime convertTo(String source, DateTime destination) {
        return DateTime.parse(source);
    }

    @Override
    public String convertFrom(DateTime source, String destination) {
        return source.toString();
    }
}
