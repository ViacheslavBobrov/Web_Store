package com.preproduction.bobrov.validator;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.bean.RegistrationFormBean;
import com.preproduction.bobrov.util.MessageManager;

import junit.framework.Assert;

import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class RegistrationFormValidatorTest {

	private RegistrationFormBean actualBean;
	private String expectedError;
	private MessageManager messageManager = new MessageManager();

	public RegistrationFormValidatorTest(RegistrationFormBean actualBean, String expectedError) {
		this.actualBean = actualBean;
		this.expectedError = expectedError;
		messageManager.setLocale(new Locale("en", "US"));
	}

	@Test
	public void testValidate() {
		RegistrationFormValidator validator = new RegistrationFormValidator();
		messageManager.setLocale(new Locale("en", "US"));
		CaptchaBean captcha = new CaptchaBean("", "8585", getCaptchaExpirationDate(1000));
		Assert.assertTrue(validator.validate(actualBean, captcha, messageManager).contains(expectedError));
	}

	@Test
	public void testValidateWhenNotValidCaptcha() {
		RegistrationFormValidator validator = new RegistrationFormValidator();
		RegistrationFormBean validBean = new RegistrationFormBean();
		validBean.setName("name");
		validBean.setSurname("surname");
		validBean.setEmail("email");
		validBean.setFirstPassword("55555");
		validBean.setSecondPassword("55555");
		validBean.setCaptchaValue("8585");
		CaptchaBean captcha = new CaptchaBean("", "8585", new Date(0));
		Assert.assertFalse(validator.validate(actualBean, captcha, messageManager).isEmpty());
	}

	/**
	 * Creates parameters for test in cycle to reduce amount of code
	 * 
	 * @return
	 */
	@Parameters
	public static Collection<Object[]> data() {

		String[][] parametersArray = { { "name", "n" }, { "surname", "#surnm^a" }, { "email@email.com", "email.com" },
				{ "55555", "pas" }, { "55555", "55558" }, { "8585", "1000" }, { "image.jpg", "image.format" } };
		String[] expectedResults = { "Not valid name", "Not valid surname", "Not valid email", "Not valid password",
				"Passwords are not equal", "Captcha is not valid",
				"Avatar image format is not valid, only *.jpg, *.png or *.gif formats allowed" };
		Object[][] parameters = new Object[7][];
		for (int i = 0; i < 7; i++) {
			RegistrationFormBean bean = new RegistrationFormBean();
			int[] indexArray = new int[7];
			indexArray[i] = 1;
			int j = 0;
			bean.setName(parametersArray[i][indexArray[j++]]);
			bean.setSurname(parametersArray[i][indexArray[j++]]);
			bean.setEmail(parametersArray[i][indexArray[j++]]);
			bean.setFirstPassword(parametersArray[i][indexArray[j++]]);
			bean.setSecondPassword(parametersArray[i][indexArray[j++]]);
			bean.setCaptchaValue(parametersArray[i][indexArray[j++]]);
			bean.setAvatar(parametersArray[i][indexArray[j++]]);
			parameters[i] = new Object[] { bean, expectedResults[i] };
		}
		return Arrays.asList(parameters);
	}

	private Date getCaptchaExpirationDate(int lifeTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, lifeTime);
		return calendar.getTime();
	}

}
