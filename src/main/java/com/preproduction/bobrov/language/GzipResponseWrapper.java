package com.preproduction.bobrov.language;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

/**
 * Wrapper over ServletOutputStream. Converts response to gzip format if content
 * has text type
 */
public class GzipResponseWrapper extends HttpServletResponseWrapper {

	private static final Logger LOG = Logger.getLogger(GzipResponseWrapper.class);
	private static final String ENCODING = "UTF-8";
	private static final String GZIP = "gzip";

	private GzipOutputStreamWrapper outputStream;
	private PrintWriter writer;
	/**
	 * Flag for checking whether content type is text
	 */
	private boolean isTextContext;

	public GzipResponseWrapper(HttpServletResponse request) {
		super(request);
	}

	@Override
	public synchronized ServletOutputStream getOutputStream() throws IOException {
		if (this.writer != null) {
			LOG.warn("Can't get output stream: getWriter() has been already called");
			throw new IllegalStateException("Can't get output stream: getWriter() has been already called");
		}
		if (!isTextContext) {
			return super.getOutputStream();
		}
		if (this.outputStream == null) {
			this.outputStream = new GzipOutputStreamWrapper(super.getOutputStream());
		}

		return this.outputStream;
	}

	@Override
	public synchronized PrintWriter getWriter() throws IOException {
		if (this.writer == null && this.outputStream != null) {
			LOG.warn("Can't get PrintWriter: getOutputStream() has been already called");
			throw new IllegalStateException("Can't get PrintWriter: getOutputStream() has been already called");
		}

		if (!isTextContext) {
			return super.getWriter();
		}

		if (this.writer == null) {
			this.outputStream = new GzipOutputStreamWrapper(super.getOutputStream());
			this.writer = new PrintWriter(new OutputStreamWriter(this.outputStream, this.getCharacterEncoding()));
		}
		return this.writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (this.writer != null) {
			this.writer.flush();
		} else if (this.outputStream != null) {
			this.outputStream.flush();
		}
		super.flushBuffer();
	}

	/**
	 * This method is empty because content length of zipped content does not
	 * match content length of unzipped content.
	 */
	@Override
	public void setContentLength(int length) {
	}

	public void close() throws IOException {
		if (this.writer != null)
			this.writer.close();
		else if (this.outputStream != null)
			this.outputStream.finish();
	}

	@Override
	public void setContentType(String type) {
		String typeToSet = type;
		if (type.contains("text")) {
			isTextContext = true;
			typeToSet = GZIP;
			getResponse().setCharacterEncoding(ENCODING);
			((HttpServletResponse) getResponse()).setHeader("Content-Encoding", GZIP);
		}
		super.setContentType(typeToSet);
	}

}
