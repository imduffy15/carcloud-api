package ie.ianduffy.carcloud.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import ch.qos.logback.classic.Logger;
import lombok.Data;

@Data
@ApiModel
public class LoggerDTO {

    @ApiModelProperty
    private String level;

    @ApiModelProperty
    private String name;

    public LoggerDTO(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    @JsonCreator
    public LoggerDTO() {
    }
}
