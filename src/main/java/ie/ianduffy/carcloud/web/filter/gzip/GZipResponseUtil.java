package ie.ianduffy.carcloud.web.filter.gzip;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public final class GZipResponseUtil {

    private static final int EMPTY_GZIPPED_CONTENT_SIZE = 20;

    private GZipResponseUtil() {
    }

    public static void addGzipHeader(final HttpServletResponse response) throws GzipResponseHeadersNotModifiableException {
        response.setHeader("Content-Encoding", "gzip");
        boolean containsEncoding = response.containsHeader("Content-Encoding");
        if (!containsEncoding) {
            throw new GzipResponseHeadersNotModifiableException("Failure when attempting to set "
                + "Content-Encoding: gzip");
        }
    }

    public static boolean shouldBodyBeZero(HttpServletRequest request, int responseStatus) {
        if (responseStatus == HttpServletResponse.SC_NO_CONTENT) {
            if (log.isDebugEnabled()) {
                log.debug("{} resulted in a {} response. Removing message body in accordance with RFC2616.",
                    request.getRequestURL(), HttpServletResponse.SC_NO_CONTENT);
            }
            return true;
        }

        if (responseStatus == HttpServletResponse.SC_NOT_MODIFIED) {
            if (log.isDebugEnabled()) {
                log.debug("{} resulted in a {} response. Removing message body in accordance with RFC2616.",
                    request.getRequestURL(), HttpServletResponse.SC_NOT_MODIFIED);
            }
            return true;
        }
        return false;
    }

    public static boolean shouldGzippedBodyBeZero(byte[] compressedBytes, HttpServletRequest request) {

        if (compressedBytes.length == EMPTY_GZIPPED_CONTENT_SIZE) {
            if (log.isTraceEnabled()) {
                log.trace("{} resulted in an empty response.", request.getRequestURL());
            }
            return true;
        } else {
            return false;
        }
    }
}
