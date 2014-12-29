package ie.ianduffy.carcloud.domain.util;

import com.javadocmd.simplelatlng.LatLng;

import org.dozer.DozerConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Arrays;
import java.util.List;

public class CustomLatLngDozerConverter extends DozerConverter<List, LatLng> {

    public CustomLatLngDozerConverter() {
        super(List.class, LatLng.class);
    }

    @Override
    public LatLng convertTo(List source, LatLng destination) {
        return new LatLng((Double) source.get(1), (Double) source.get(0));
    }

    @Override
    public List convertFrom(LatLng source, List destination) {
        return Arrays.asList(source.getLatitude(), source.getLongitude());
    }
}
