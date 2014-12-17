package ie.ianduffy.carcloud.web.dto;

import lombok.Data;

@Data
public class MunicTrackDTO {
    private MunicMetadataDTO meta;
    private MunicPayloadDTO payload;
}
