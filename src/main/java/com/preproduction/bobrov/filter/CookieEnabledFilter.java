package com.preproduction.bobrov.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.constant.Message;
import com.preproduction.bobrov.constant.Path;
import com.preproduction.bobrov.util.MessageManager;

/**
 * Servlet Filter that blocks specefied urls if cookies are not enabled
 */
public class CookieEnabledFilter implements Filter {

	/**
	 * List of of url that are filtered
	 */
	private List<String> filteredUrls;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (isAccessAllowed(request)) {
			chain.doFilter(request, response);
		} else {
			MessageManager manager = (MessageManager) request.getServletContext().getAttribute(AttributeKey.MESSAGE_MANAGER); 
			request.setAttribute(AttributeKey.ERROR, manager.getMessage(Message.ENABLE_COOKIE));
			request.getRequestDispatcher(Path.ERROR_PAGE).forward(request, response);
		}
	}
 
	public void init(FilterConfig fConfig) throws ServletException {
		filteredUrls = asList(fConfig.getInitParameter(AttributeKey.FILTERED_URLS));
	}

	private boolean isAccessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String url = httpRequest.getServletPath().substring(1);
		return filteredUrls.contains(url) && (httpRequest.getMethod() == "POST") 
				&& (httpRequest.getCookies() == null) ? false : true;
	}

	/**
	 * Extracts list of url that are filtered
	 * 
	 * @param str
	 * @return
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}
}
