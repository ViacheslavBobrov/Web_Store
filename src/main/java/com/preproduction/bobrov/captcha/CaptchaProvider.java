package com.preproduction.bobrov.captcha;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.preproduction.bobrov.bean.CaptchaBean;

/**
 * Interface for captcha provider
 */
public interface CaptchaProvider {

	/**
	 * Saves captcha data into provider storage
	 * @param captcha captcha to save
	 * @param request
	 * @param response
	 */
	void putCaptcha(CaptchaBean captcha, HttpServletRequest request, HttpServletResponse response);

	/**
	 * Returns captcha object from provider storage
	 * @param request
	 * @return captcha object from provider storage or null if captcha was not found
	 */
	CaptchaBean getCaptcha(HttpServletRequest request);

	/**
	 * Removes captcha from provider storage
	 * @param request
	 */
	void removeCaptcha(HttpServletRequest request);	
}
