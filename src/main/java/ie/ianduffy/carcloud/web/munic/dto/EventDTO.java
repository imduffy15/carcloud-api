package ie.ianduffy.carcloud.web.munic.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Map;

import lombok.Data;

@Data
@ApiModel
public class EventDTO {

    @ApiModelProperty
    private MetadataDTO meta;

    @ApiModelProperty
    private Map<String, Object> payload;
}
