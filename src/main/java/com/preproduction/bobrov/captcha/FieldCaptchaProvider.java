package com.preproduction.bobrov.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.constant.AttributeKey;

/**
 * Captcha provider that manipulates captcha data using hidden field
 */
public class FieldCaptchaProvider extends ContextCaptchaProvider {

	/**
	 * Saves captcha data into captchaMap and sets captcha id into request attribute
	 * @param captcha captcha to save
	 * @param request
	 * @param response
	 */
	@Override
	public void putCaptcha(CaptchaBean captcha, HttpServletRequest request, HttpServletResponse response) {
		captchaMap.put(captcha.getId(), captcha);
		request.setAttribute(AttributeKey.CAPTCHA_ID, captcha.getId());
	}

	/**
	 * Returns captcha from captchaMap by captchaId that is obtained from parameter
	 * @param request
	 * @return captcha from captchaMap
	 */
	@Override
	public CaptchaBean getCaptcha(HttpServletRequest request) {
		String captchaId = request.getParameter(AttributeKey.CAPTCHA_ID);
		CaptchaBean captcha = captchaMap.get(captchaId);
		return captcha;
	}


	/**
	 * Removes captcha from captchaMap by captchaId that is obtained from request
	 */
	@Override
	public void removeCaptcha(HttpServletRequest request) {
		String captchaId = request.getParameter(AttributeKey.CAPTCHA_ID);
		captchaMap.remove(captchaId);
	}

}
