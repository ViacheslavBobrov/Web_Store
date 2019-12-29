package com.preproduction.bobrov.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.bean.RegistrationFormBean;
import com.preproduction.bobrov.constant.Message;
import com.preproduction.bobrov.util.MessageManager;

/**
 * Validates fields from registration form
 */
public class RegistrationFormValidator {

	private static final String NAME_REGEXP = "^[a-zA-Z�-��-�0-9]{2,25}$";
	private static final String EMAIL_REGEXP = "^\\w+([-\\.]\\w+)*@\\w+([-\\.]\\w+)*\\.\\w+([-\\.]\\w+)*$";			
	private static final String PASSWORD_REGEXP = "^[a-zA-Z0-9]{5,25}$";
	private static final String AVATAR_REGEXP = ".*\\.(jpg|png|gif)$";

	/**
	 * Validates fields from registration form
	 * @param registrationBean input data fields
	 * @param captcha input expected captcha
	 * @return list of errors
	 */
	public List<String> validate(RegistrationFormBean registrationBean, CaptchaBean captcha, MessageManager messageManager) {
		List<String> errors = new ArrayList<String>();

		if (captcha == null || new Date().after(captcha.getExpirationDate())) {
			errors.add(messageManager.getMessage(Message.REGISTRATION_TIMEOUT));

		} else {

			if (!checkRegexp(registrationBean.getName(), NAME_REGEXP)) {
				errors.add(messageManager.getMessage(Message.NOT_VALID_NAME));
			}
			if (!checkRegexp(registrationBean.getSurname(), NAME_REGEXP)) {
				errors.add(messageManager.getMessage(Message.NOT_VALID_SURNAME));
			}
			if (!checkRegexp(registrationBean.getEmail(), EMAIL_REGEXP)) {
				errors.add(messageManager.getMessage(Message.NOT_VALID_EMAIL));
			}
			if (!checkRegexp(registrationBean.getFirstPassword(), PASSWORD_REGEXP)) {
				errors.add(messageManager.getMessage(Message.NOT_VALID_PASSWORD));
			}
			if (!registrationBean.getFirstPassword().equals(registrationBean.getSecondPassword())) {
				errors.add(messageManager.getMessage(Message.PASSWORDS_NOT_EQUAL));
			}
			if (!captcha.getValue().equals(registrationBean.getCaptchaValue())) {
				errors.add(messageManager.getMessage(Message.CAPTCHA_NOT_VALID));
			}
			if(!registrationBean.getAvatar().isEmpty() && !checkRegexp(registrationBean.getAvatar(), AVATAR_REGEXP)) {
				errors.add(messageManager.getMessage(Message.AVATAR_NOT_VALID));
			}
		}
		return errors;
	}

	private static boolean checkRegexp(String field, String regexp) {
		if (field == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(field);
		return matcher.matches();
	}

}
