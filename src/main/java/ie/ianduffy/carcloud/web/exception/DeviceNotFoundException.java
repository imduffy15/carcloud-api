package ie.ianduffy.carcloud.web.exception;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException() {
        super("No such device");
    }

    public DeviceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DeviceNotFoundException(final String message) {
        super(message);
    }

    public DeviceNotFoundException(final Throwable cause) {
        super(cause);
    }

}
