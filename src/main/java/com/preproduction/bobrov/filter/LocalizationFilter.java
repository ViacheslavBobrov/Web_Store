package com.preproduction.bobrov.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.exception.InitializationException;
import com.preproduction.bobrov.language.LanguageProvider;
import com.preproduction.bobrov.language.LanguageRequestWrapper;
import com.preproduction.bobrov.util.MessageManager;

/**
 * Defines and sets localization for user
 */
public class LocalizationFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(LocalizationFilter.class);
	/**
	 * List of allowed languages
	 */
	private List<String> acceptedLanguages;
	private Locale defaultLanguage;
	private LanguageProvider languageProvider;

	private static final String POSITIVE_INTEGER = "^[1-9]\\d*$";

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		Locale selectedLanguage = getSelectedLanguage(httpRequest, httpResponse);
		HttpSession session = httpRequest.getSession();
		MessageManager messageManager = (MessageManager) session.getAttribute(AttributeKey.MESSAGE_MANAGER);
		messageManager.setLocale(selectedLanguage);

		if (languageProvider != null) {
			languageProvider.setLanguage(selectedLanguage, httpRequest, httpResponse);
		}
		LanguageRequestWrapper wrappedRequest = new LanguageRequestWrapper(httpRequest, selectedLanguage);
		chain.doFilter(wrappedRequest, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		acceptedLanguages = asList(fConfig.getInitParameter(AttributeKey.ACCEPTED_LANGUAGES));
		String languageIdParameter = fConfig.getInitParameter(AttributeKey.DEFAULT_LANGUAGE);

		if (!isValidId(languageIdParameter)) {
			LOG.warn("Not valid default language id parameter");
			throw new InitializationException("Not valid default language id parameter");
		}
		int languageId = Integer.parseInt(languageIdParameter);
		defaultLanguage = new Locale(acceptedLanguages.get(languageId - 1));
		languageProvider = (LanguageProvider) fConfig.getServletContext().getAttribute(AttributeKey.LANGUAGE_PROVIDER);

	}

	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

	/**
	 * Returns selected language
	 */
	private Locale getSelectedLanguage(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Locale language = null;
		String languageParameter = httpRequest.getParameter(AttributeKey.LANGUAGE_PARAMETER);
		if (acceptedLanguages.contains(languageParameter)) {

			language = new Locale(languageParameter);

		} else {
			if (languageProvider != null) {
				language = languageProvider.getLanguage(httpRequest);
			}
			if (language == null) {

				Enumeration<Locale> locales = httpRequest.getLocales();
				Locale headerLocale = null;
				while (locales.hasMoreElements()) {
					Locale nextLocale = locales.nextElement();
					if (acceptedLanguages.contains(nextLocale.toString())) {
						headerLocale = nextLocale;
						break;
					}
				}
				if (headerLocale != null) {
					language = headerLocale;
				} else {
					language = defaultLanguage;
				}
			}
		}
		return language;
	}

	private boolean isValidId(String idParameter) {
		Pattern pattern = Pattern.compile(POSITIVE_INTEGER);
		Matcher matcher = pattern.matcher(idParameter);
		return matcher.matches();
	}
}
