package ie.ianduffy.carcloud.web.munic.dto;

import java.util.Map;

import lombok.Data;

@Data
public class EventDTO {

    private MetadataDTO meta;

    private Map<String, Object> payload;
}
