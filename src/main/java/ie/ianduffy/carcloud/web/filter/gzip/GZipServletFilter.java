package ie.ianduffy.carcloud.web.filter.gzip;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

@Slf4j
public class GZipServletFilter implements Filter {

    private boolean acceptsGZipEncoding(HttpServletRequest httpRequest) {
        String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
        return acceptEncoding != null && acceptEncoding.contains("gzip");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isIncluded(httpRequest) && acceptsGZipEncoding(httpRequest) && !response.isCommitted()) {
            if (log.isTraceEnabled()) {
                log.trace("{} Written with gzip compression", httpRequest.getRequestURL());
            }

            final ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            final GZIPOutputStream gzout = new GZIPOutputStream(compressed);

            final GZipServletResponseWrapper wrapper = new GZipServletResponseWrapper(httpResponse, gzout);
            wrapper.setDisableFlushBuffer(true);
            chain.doFilter(request, wrapper);
            wrapper.flush();

            gzout.close();

            if (response.isCommitted()) {
                return;
            }

            switch (wrapper.getStatus()) {
                case HttpServletResponse.SC_NO_CONTENT:
                case HttpServletResponse.SC_RESET_CONTENT:
                case HttpServletResponse.SC_NOT_MODIFIED:
                    return;
                default:
            }

            byte[] compressedBytes = compressed.toByteArray();
            boolean shouldGzippedBodyBeZero = GZipResponseUtil.shouldGzippedBodyBeZero(compressedBytes, httpRequest);
            boolean shouldBodyBeZero = GZipResponseUtil.shouldBodyBeZero(httpRequest, wrapper.getStatus());
            if (shouldGzippedBodyBeZero || shouldBodyBeZero) {
                response.setContentLength(0);
                return;
            }

            // Write the zipped body
            GZipResponseUtil.addGzipHeader(httpResponse);

            response.setContentLength(compressedBytes.length);

            response.getOutputStream().write(compressedBytes);

        } else {
            if (log.isTraceEnabled()) {
                log.trace("{} Written without gzip compression because the request does not accept gzip", httpRequest.getRequestURL());
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private boolean isIncluded(final HttpServletRequest request) {
        final String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        final boolean includeRequest = !(uri == null);

        if (includeRequest && log.isDebugEnabled()) {
            log.debug("{} resulted in an include request. This is unusable, because"
                    + "the response will be assembled into the overrall response. Not gzipping.",
                request.getRequestURL());
        }
        return includeRequest;
    }
}
