package ie.ianduffy.carcloud.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javadocmd.simplelatlng.LatLng;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties({"id_str", "id", "recorded_at"})
public class PayloadDTO {
    private Long deviceId;
    private Map<String, Map<String, String>> fields;

    private LatLng location;
    private DateTime receivedAt;
    private DateTime recordedAt;

    PayloadDTO() {
    }

    @JsonProperty("asset")
    public Long getDeviceId() {
        return deviceId;
    }

    @JsonProperty("asset")
    public void setDeviceId(String deviceId) {
        this.deviceId =  Long.parseLong(deviceId);
    }

    public Map<String, Map<String, String>> getFields() {
        return fields;
    }

    public void setFields(Map<String, Map<String, String>> fields) {
        this.fields = fields;
    }

    @JsonProperty("loc")
    public List<Double> getLocation() {
        return Arrays.asList(location.getLongitude(), location.getLatitude());
    }

    @JsonProperty("loc")
    public void setLocation(List<Double> loc) {
        this.location = new LatLng(loc.get(1), loc.get(0));
    }

    @JsonProperty("received_at")
    public DateTime getReceivedAt() {
        return receivedAt;
    }

    @JsonProperty("received_at")
    public void setReceivedAt(DateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    @JsonProperty("recorded_at_ms")
    public DateTime getRecordedAt() {
        return recordedAt;
    }

    @JsonProperty("recorded_at_ms")
    public void setRecordedAt(DateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PayloadDTO{");
        sb.append(", deviceId='").append(deviceId).append('\'');
        sb.append(", recorededAt='").append(recordedAt).append('\'');
        sb.append(", receivedAt='").append(receivedAt).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", fields='").append(fields).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
