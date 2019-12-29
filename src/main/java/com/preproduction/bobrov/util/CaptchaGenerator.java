package com.preproduction.bobrov.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletContext;

import com.preproduction.bobrov.bean.CaptchaBean;

/**
 * Generates new captcha
 */
public class CaptchaGenerator {
	
	private static final int BEGIN_VALUE = 1000;
	private static final int RANGE_VALUE = 8999;
	
	private Random randomNumber = new Random(47);//TODO: fix
		
	public CaptchaBean create(int lifeTime) {
		String captchaId = UUID.randomUUID().toString();
		String captchaValue = String.valueOf(BEGIN_VALUE + randomNumber.nextInt(RANGE_VALUE));
		Date captchaExopirationDate = getCaptchaExpirationDate(lifeTime);
		return new CaptchaBean(captchaId, captchaValue, captchaExopirationDate);
	}
	
	private Date getCaptchaExpirationDate(int lifeTime) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(new Date()); 
		calendar.add(Calendar.SECOND, lifeTime);		 
		return calendar.getTime();
	}
	
	
}
