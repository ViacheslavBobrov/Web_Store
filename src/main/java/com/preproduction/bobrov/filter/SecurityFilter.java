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

import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.constant.Path;
import com.preproduction.bobrov.entity.User;
import com.preproduction.bobrov.language.LanguageProvider;
import com.preproduction.bobrov.service.SecurityService;

/**
 * Filter for managing user access to web application
 */
public class SecurityFilter implements Filter {

	private SecurityService securityService;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String url = httpRequest.getServletPath();
		if (securityService.isPathRestricted(url)) {
			User user = (User) httpRequest.getSession().getAttribute(AttributeKey.USER);
			if (user == null) {
				httpRequest.setAttribute(AttributeKey.UNAUTHORIZED_REQUEST, url.substring(1));
				httpRequest.getRequestDispatcher(Path.LOGIN).forward(request, response);
			} else if (!securityService.isUserAllowed(url, user)) {
				httpResponse.sendRedirect("accessRestricted");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		securityService = (SecurityService) fConfig.getServletContext().getAttribute(AttributeKey.SECURITY_SERVICE);
	}

}
