package com.preproduction.bobrov.language;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provider for manipulating user's locale 
 */
public interface LanguageProvider {

	Locale getLanguage(HttpServletRequest request);
	
	void setLanguage(Locale language, HttpServletRequest request, HttpServletResponse response);
}
