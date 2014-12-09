package ie.ianduffy.carcloud.web.exception.dto;

import lombok.Data;

@Data
public class FieldErrorDTO {
    private String field;
    private String message;

    public FieldErrorDTO() {
    }

    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
