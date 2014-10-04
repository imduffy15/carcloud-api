package ie.ianduffy.carcloud.web.filter.gzip;

import javax.servlet.ServletException;

class GzipResponseHeadersNotModifiableException extends ServletException {

    public GzipResponseHeadersNotModifiableException(String message) {
        super(message);
    }
}
