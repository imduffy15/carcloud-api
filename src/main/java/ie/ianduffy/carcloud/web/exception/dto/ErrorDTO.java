package ie.ianduffy.carcloud.web.exception.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    private Object error;

    public ErrorDTO() {
    }

    public ErrorDTO(Object error) {
        this.error = error;
    }
}
