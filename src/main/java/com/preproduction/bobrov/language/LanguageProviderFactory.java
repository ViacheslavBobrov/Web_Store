package com.preproduction.bobrov.language;

import org.apache.log4j.Logger;

public class LanguageProviderFactory {

	private static final Logger LOG = Logger.getLogger(LanguageProviderFactory.class);

	public LanguageProvider createProvider(String languageProviderType) {
		LanguageProvider provider = null;
		switch (languageProviderType) {
		case "session":
			provider = new SessionLanguageProvider();
			break;
		case "cookie":
			provider = new CookieLanguageProvider();
			break;
		default:
			LOG.warn("Not valid language provider type '" + languageProviderType + "'");			
		}
		return provider;
	}

}
