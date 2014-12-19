package ie.ianduffy.carcloud.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import ch.qos.logback.classic.Logger;
import lombok.Data;

@Data
public class LoggerDTO {

    private String level;

    private String name;

    public LoggerDTO(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    @JsonCreator
    public LoggerDTO() {
    }
}
