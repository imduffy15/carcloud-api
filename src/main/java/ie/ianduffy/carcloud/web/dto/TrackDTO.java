package ie.ianduffy.carcloud.web.dto;

import lombok.Data;

@Data
public class TrackDTO {
    private MetadataDTO meta;
    private PayloadDTO payload;
}
