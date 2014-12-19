package ie.ianduffy.carcloud.web.filter.gzip;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

class GZipServletOutputStream extends ServletOutputStream {

    private final OutputStream stream;

    public GZipServletOutputStream(OutputStream output) {
        super();
        this.stream = output;
    }

    @Override
    public void write(int b) throws IOException {
        this.stream.write(b);
    }

    @Override
    public void write(byte b[]) throws IOException {
        this.stream.write(b);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        this.stream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        this.stream.flush();
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}
