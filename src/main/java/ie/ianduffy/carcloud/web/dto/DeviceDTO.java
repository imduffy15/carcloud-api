package ie.ianduffy.carcloud.web.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class DeviceDTO extends AbstractAuditingEntityDTO<Long> {

    @ApiModelProperty
    @Size(min = 1, max = 150)
    private String description;

    @ApiModelProperty
    private Long id;
}
