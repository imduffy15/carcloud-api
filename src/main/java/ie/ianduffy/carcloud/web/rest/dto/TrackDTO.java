package ie.ianduffy.carcloud.web.rest.dto;

public class TrackDTO {

    private MetadataDTO meta;
    private PayloadDTO payload;

    TrackDTO() {

    }

    public MetadataDTO getMeta() {
        return meta;
    }

    public void setMeta(MetadataDTO meta) {
        this.meta = meta;
    }

    public PayloadDTO getPayload() {
        return payload;
    }

    public void setPayload(PayloadDTO payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TrackDTO{");
        sb.append(", meta='").append(meta).append('\'');
        sb.append(", payload='").append(payload).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
