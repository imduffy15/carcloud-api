package ie.ianduffy.carcloud.web.filter.gzip;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

class GZipServletResponseWrapper extends HttpServletResponseWrapper {

    private boolean disableFlushBuffer = false;
    private GZipServletOutputStream gzipOutputStream = null;
    private PrintWriter printWriter = null;

    public GZipServletResponseWrapper(HttpServletResponse response, GZIPOutputStream gzout)
        throws IOException {
        super(response);
        gzipOutputStream = new GZipServletOutputStream(gzout);
    }

    public void close() throws IOException {

        if (this.printWriter != null) {
            this.printWriter.close();
        }

        if (this.gzipOutputStream != null) {
            this.gzipOutputStream.close();
        }
    }

    public void flush() throws IOException {
        if (printWriter != null) {
            printWriter.flush();
        }

        if (gzipOutputStream != null) {
            gzipOutputStream.flush();
        }
    }

    @Override
    public void flushBuffer() throws IOException {

        if (this.printWriter != null) {
            this.printWriter.flush();
        }

        if (this.gzipOutputStream != null) {
            this.gzipOutputStream.flush();
        }
        if (!disableFlushBuffer) {
            super.flushBuffer();
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.printWriter != null) {
            throw new IllegalStateException(
                "PrintWriter obtained already - cannot get OutputStream");
        }

        return this.gzipOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.printWriter == null) {
            this.gzipOutputStream = new GZipServletOutputStream(
                getResponse().getOutputStream());

            this.printWriter = new PrintWriter(new OutputStreamWriter(
                this.gzipOutputStream, getResponse().getCharacterEncoding()), true);
        }

        return this.printWriter;
    }

    @Override
    public void setContentLength(int length) {
    }

    public void setDisableFlushBuffer(boolean disableFlushBuffer) {
        this.disableFlushBuffer = disableFlushBuffer;
    }
}
