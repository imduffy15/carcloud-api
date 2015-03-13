package ie.ianduffy.carcloud.web.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class AlertDTO extends AbstractAuditingEntityDTO<Long> {

    @ApiModelProperty
    private Long id;

    public AlertDTO() {

    }
}
