package ie.ianduffy.carcloud.web.munic.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel
public class EventDTO {

    @ApiModelProperty
    private MetadataDTO meta;

    @ApiModelProperty
    private Map<String, Object> payload;
}
