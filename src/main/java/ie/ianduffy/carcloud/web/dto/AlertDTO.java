package ie.ianduffy.carcloud.web.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class AlertDTO extends AbstractAuditingEntityDTO<Long> {

    @ApiModelProperty
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    private LocalTime after;
    @ApiModelProperty
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    private LocalTime before;
    @ApiModelProperty
    private Long id;

    public AlertDTO() {

    }
}
