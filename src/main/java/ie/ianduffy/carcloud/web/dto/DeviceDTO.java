package ie.ianduffy.carcloud.web.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class DeviceDTO extends AbstractAuditingEntityDTO<Long> {

    @NotNull
    @ApiModelProperty
    @Size(min = 1, max = 100)
    private String description;

    @NotNull
    @ApiModelProperty
    private Long id;
}
