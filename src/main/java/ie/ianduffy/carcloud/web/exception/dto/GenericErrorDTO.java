package ie.ianduffy.carcloud.web.exception.dto;

import lombok.Data;

@Data
public class GenericErrorDTO {

    private String exception;

    private String message;

    public GenericErrorDTO() {

    }

    public GenericErrorDTO(String exception, String message) {
        this.exception = exception;
        this.message = message;
    }
}
