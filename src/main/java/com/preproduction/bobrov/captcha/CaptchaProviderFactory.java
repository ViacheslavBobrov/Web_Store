package com.preproduction.bobrov.captcha;

import org.apache.log4j.Logger;

public class CaptchaProviderFactory {

	private static final Logger LOG = Logger.getLogger(CaptchaProviderFactory.class);
	
	public CaptchaProvider createProvider(String captchaProviderType) {
		CaptchaProvider provider = null;
		switch (captchaProviderType) {
		case "session":
			provider = new SessionCaptchaProvider();
			break;
		case "cookie":
			provider = new CookieCaptchaProvider();
			break;
		case "field":
			provider = new FieldCaptchaProvider();
			break;
		default:
			LOG.warn("Not valid captha provider type '" + captchaProviderType + "'");
			throw new IllegalArgumentException("Not valid captha provider type '" + captchaProviderType + "'");			
		}
		return provider;
	}

}
