package com.preproduction.bobrov.captcha;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.constant.AttributeKey;

/**
 * Captcha provider that manipulates captcha data using cookies
 */
public class CookieCaptchaProvider extends ContextCaptchaProvider {

	private static final int COOKIE_MAX_AGE = 60;

	/**
	 * Saves captcha data into captchaMap and sets captcha id into user cookie
	 * @param captcha captcha to save
	 * @param request
	 * @param response
	 */
	@Override
	public void putCaptcha(CaptchaBean captcha, HttpServletRequest request, HttpServletResponse response) {

		captchaMap.put(captcha.getId(), captcha);
		Cookie cookie = new Cookie(AttributeKey.CAPTCHA_ID, captcha.getId());
		cookie.setMaxAge(COOKIE_MAX_AGE);
		response.addCookie(cookie);
	}

	/**
	 * Returns captcha from captchaMap by captchaId that is obtained from request
	 * @param request
	 * @return captcha from captchaMap
	 */
	@Override
	public CaptchaBean getCaptcha(HttpServletRequest request) {

		return captchaMap.get(obtainCaptchaId(request));
	}

	/**
	 * Removes captcha from captchaMap by captchaId that is obtained from request
	 */
	@Override
	public void removeCaptcha(HttpServletRequest request) {
		captchaMap.remove(obtainCaptchaId(request));
	}

	/**
	 * Obtains captchaId from user cookies
	 * @param request
	 * @return captchaId from user cookies
	 */
	private String obtainCaptchaId(HttpServletRequest request) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(AttributeKey.CAPTCHA_ID)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
