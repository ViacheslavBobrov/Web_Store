package com.preproduction.bobrov.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.preproduction.bobrov.language.GzipResponseWrapper;

/**
 * Filter compress response into gzip 
 */
public class GzipContentFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String acceptEncoding = httpRequest.getHeader("Accept-encoding");
		if ((acceptEncoding != null) && acceptEncoding.contains("gzip")) {
			
			GzipResponseWrapper responseWrapper = new GzipResponseWrapper(httpResponse);
			
			chain.doFilter(request, responseWrapper);
			
			responseWrapper.close();
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
