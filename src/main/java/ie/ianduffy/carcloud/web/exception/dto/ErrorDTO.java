package ie.ianduffy.carcloud.web.exception.dto;

public class ErrorDTO {
    private Object error;

    public ErrorDTO() {

    }

    public ErrorDTO(Object error) {
        this.error = error;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
