package ie.ianduffy.carcloud.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Map;

public class PayloadDTO {
    private String asset;
    private Map<String, Map<String, String>> fields;
    private Long id;
    private String idStr;
    private ArrayList<Float> loc;
    private DateTime receivedAt;
    private DateTime recordedAt;
    private DateTime recordedAtMs;

    PayloadDTO() {
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Map<String, Map<String, String>> getFields() {
        return fields;
    }

    public void setFields(Map<String, Map<String, String>> fields) {
        this.fields = fields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("id_str")
    public String getIdStr() {
        return idStr;
    }

    @JsonProperty("id_str")
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public ArrayList<Float> getLoc() {
        return loc;
    }

    public void setLoc(ArrayList<Float> loc) {
        this.loc = loc;
    }

    @JsonProperty("received_at")
    public DateTime getReceivedAt() {
        return receivedAt;
    }

    @JsonProperty("received_at")
    public void setReceivedAt(DateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    @JsonProperty("recorded_at")
    public DateTime getRecordedAt() {
        return recordedAt;
    }

    @JsonProperty("recorded_at")
    public void setRecordedAt(DateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    @JsonProperty("recorded_at_ms")
    public DateTime getRecordedAtMs() {
        return recordedAtMs;
    }

    @JsonProperty("recorded_at_ms")
    public void setRecordedAtMs(DateTime recordedAtMs) {
        this.recordedAtMs = recordedAtMs;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PayloadDTO{");
        sb.append(", id='").append(id).append('\'');
        sb.append(", idStr='").append(idStr).append('\'');
        sb.append(", asset='").append(asset).append('\'');
        sb.append(", recorededAt='").append(recordedAt).append('\'');
        sb.append(", recorededAtMs='").append(recordedAtMs).append('\'');
        sb.append(", receivedAt='").append(receivedAt).append('\'');
        sb.append(", loc='").append(loc).append('\'');
        sb.append(", fields='").append(fields).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
