package ie.ianduffy.carcloud.web.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import ie.ianduffy.carcloud.domain.Field;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import java.util.List;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class AlertDTO extends AbstractAuditingEntityDTO<Long> {

    @ApiModelProperty
    private Long id;

    public AlertDTO() {

    }
}
