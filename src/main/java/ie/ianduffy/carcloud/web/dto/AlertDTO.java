package ie.ianduffy.carcloud.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import ie.ianduffy.carcloud.domain.AlertFieldWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import java.util.List;

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
    private String description;
    @ApiModelProperty
    private Long id;

    @ApiModelProperty
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private List<AlertFieldWrapper> fields;

    public AlertDTO() {

    }
}
