package ie.ianduffy.carcloud.web.exception.dto;

public class GenericErrorDTO {
    private String exception;
    private String message;

    public GenericErrorDTO() {

    }

    public GenericErrorDTO(String exception, String message) {
        this.exception = exception;
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
