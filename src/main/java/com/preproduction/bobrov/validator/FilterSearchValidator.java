package com.preproduction.bobrov.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Validates fields from filter search form
 */
public class FilterSearchValidator {

	private static final String POSITIVE_INTEGER = "^[1-9]\\d*$";
	private static final String POSITIVE_DOUBLE = "^\\d+(\\.\\d+)*$";
	
	public boolean checkIsPositiveInteger(String value) {
		return checkRegexp(value, POSITIVE_INTEGER);
	}
	
	public boolean checkIsPositiveDouble(String value) {
		return checkRegexp(value, POSITIVE_DOUBLE);
	}
	
	private boolean checkRegexp(String field, String regexp) {
		if (field == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(field);
		return matcher.matches();
	}
	
}
