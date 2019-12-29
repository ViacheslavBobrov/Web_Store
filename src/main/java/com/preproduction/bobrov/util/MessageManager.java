package com.preproduction.bobrov.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

	private static final String RESOURCES = "resources";

	private ResourceBundle resourceBundle;

	public void setLocale(Locale locale) {
		resourceBundle = ResourceBundle.getBundle(RESOURCES, locale);
	}

	public String getMessage(String key) {
		return resourceBundle.getString(key);
	}

}
