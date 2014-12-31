package ie.ianduffy.carcloud.web.munic.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
@ApiModel
public class MetadataDTO {

    @ApiModelProperty
    private String account;

    @ApiModelProperty
    private String event;
}
