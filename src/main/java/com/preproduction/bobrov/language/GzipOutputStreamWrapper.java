package com.preproduction.bobrov.language;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * Wrapper upon ServletOutputStream 
 */
public class GzipOutputStreamWrapper extends ServletOutputStream {

	private final ServletOutputStream outputStream;
	private final GZIPOutputStream gzipOutputStream;

	public GzipOutputStreamWrapper(ServletOutputStream servletOutputStream) throws IOException {
		this.outputStream = servletOutputStream;
		this.gzipOutputStream = new GZIPOutputStream(servletOutputStream);
	}

	@Override
	public boolean isReady() {
		return this.outputStream.isReady();
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		this.outputStream.setWriteListener(writeListener);
	}

	@Override
	public void write(int b) throws IOException {
		this.gzipOutputStream.write(b);
	}

	@Override
	public void close() throws IOException {
		this.gzipOutputStream.close();
	}

	@Override
	public void flush() throws IOException {
		this.gzipOutputStream.flush();
	}

	public void finish() throws IOException {
		this.gzipOutputStream.finish();
	}
}
