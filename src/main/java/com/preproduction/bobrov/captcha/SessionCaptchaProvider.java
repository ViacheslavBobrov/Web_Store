package com.preproduction.bobrov.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.util.CaptchaGenerator;

/**
 * Captcha provider that manipulates captcha data using session
 */
public class SessionCaptchaProvider implements CaptchaProvider {	
	
	/**
	 * Saves captcha data into session
	 * @param captcha captcha to save
	 * @param request
	 * @param response
	 */
	public void putCaptcha(CaptchaBean captcha, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();		
		session.setAttribute(AttributeKey.CAPTCHA, captcha);		
	}
	/**
	 * Returns captcha from session
	 */
	public CaptchaBean getCaptcha(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (CaptchaBean) session.getAttribute(AttributeKey.CAPTCHA);
	}
	
	/**
	 * Removes captcha from session
	 */
	public void removeCaptcha(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(AttributeKey.CAPTCHA);
	}

}
