package com.preproduction.bobrov.language;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.preproduction.bobrov.constant.AttributeKey;

/**
 *	language provider that manipulates locale data using session 
 */
public class SessionLanguageProvider implements LanguageProvider {

	@Override
	public Locale getLanguage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (Locale) session.getAttribute(AttributeKey.SET_LANGUAGE);
	}

	@Override
	public void setLanguage(Locale language, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute(AttributeKey.SET_LANGUAGE, language);
	}

}
