package ie.ianduffy.carcloud.web.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import ie.ianduffy.carcloud.domain.AlertFieldWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import java.util.List;

@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
public class AlertDTO extends AbstractAuditingEntityDTO<Long> {

    @ApiModelProperty
    private LocalTime after;

    @ApiModelProperty
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

    @JsonIgnore
    public LocalTime getAfter() {
        return after;
    }

    @JsonIgnore
    public LocalTime getBefore() {
        return before;
    }

    @JsonProperty("after")
    public void setAfter(LocalTime after) {
        this.after = after;
    }

    @JsonProperty("before")
    public void setBefore(LocalTime before) {
        this.before = before;
    }

    @JsonProperty("after")
    public String getAfterAsString() {
        if(after != null) return after.toString();
        return null;
    }

    @JsonProperty("before")
    public String getBeforeAsString() {
        if(before != null) return before.toString();
        return null;
    }
}
