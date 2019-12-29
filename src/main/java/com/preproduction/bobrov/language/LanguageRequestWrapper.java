package com.preproduction.bobrov.language;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wrapper over HttpServletRequestWrapper. Replaces request's locale to
 * specified locale
 */
public class LanguageRequestWrapper extends HttpServletRequestWrapper {

	private Locale locale;

	public LanguageRequestWrapper(ServletRequest request, Locale locale) {
		super((HttpServletRequest) request);
		this.locale = locale;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return Collections.enumeration(Arrays.asList(locale));
	}

}
