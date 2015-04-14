package ie.ianduffy.carcloud.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import ie.ianduffy.carcloud.domain.AlertFieldWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.LocalTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class AlertDTO extends AbstractAuditingEntityDTO<Long> {

    @ApiModelProperty
    private LocalTime after;

    @ApiModelProperty
    private LocalTime before;

    @NotNull
    @ApiModelProperty
    @Size(min = 1, max = 100)
    private String description;

    @ApiModelProperty
    private List<AlertFieldWrapper> fields;

    @ApiModelProperty
    private Long id;

    public AlertDTO() {

    }

    @JsonIgnore
    public LocalTime getAfter() {
        return after;
    }

    @JsonProperty("after")
    public void setAfter(LocalTime after) {
        this.after = after;
    }

    @JsonProperty("after")
    public String getAfterAsString() {
        if (after != null) return after.toString();
        return null;
    }

    @JsonIgnore
    public LocalTime getBefore() {
        return before;
    }

    @JsonProperty("before")
    public void setBefore(LocalTime before) {
        this.before = before;
    }

    @JsonProperty("before")
    public String getBeforeAsString() {
        if (before != null) return before.toString();
        return null;
    }
}
