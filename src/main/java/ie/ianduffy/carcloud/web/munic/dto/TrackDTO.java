package ie.ianduffy.carcloud.web.munic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javadocmd.simplelatlng.LatLng;

import org.dozer.Mapping;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackDTO {

    private Long deviceId;

    private Map<String, Map<String, String>> fields;

    private LatLng location;

    private DateTime receivedAt;

    private DateTime recordedAt;
}
