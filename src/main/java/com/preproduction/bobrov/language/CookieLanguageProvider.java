package com.preproduction.bobrov.language;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.preproduction.bobrov.constant.AttributeKey;

/**
 *	language provider that manipulates locale data using cookies 
 */
public class CookieLanguageProvider implements LanguageProvider {

	@Override
	public Locale getLanguage(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(AttributeKey.SET_LANGUAGE)) {
					return new Locale(cookie.getValue());					 
				}
			}
		}
		return null;
	}

	@Override
	public void setLanguage(Locale language, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(AttributeKey.SET_LANGUAGE, language.getLanguage());
		int cookieLifetime = (int) request.getServletContext().getAttribute(AttributeKey.LANGUAGE_COOKIE_LIFETIME);
		cookie.setMaxAge(cookieLifetime);
		response.addCookie(cookie);
	}

}
