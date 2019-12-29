package com.preproduction.bobrov.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.captcha.CaptchaProvider;
import com.preproduction.bobrov.constant.AttributeKey;
import com.github.cage.Cage;
import com.github.cage.GCage;

/**
 * Servlet for drawing captcha image by captcha id
 */
public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(CaptchaServlet.class);

	private static final Cage cage = new GCage();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CaptchaProvider captchaProvider = (CaptchaProvider) request.getServletContext().getAttribute(AttributeKey.CAPTCHA_PROVIDER);
		CaptchaBean captcha = captchaProvider.getCaptcha(request);
		setResponseHeaders(response);
		if (captcha != null) {
			cage.draw(captcha.getValue(), response.getOutputStream());
		} else {
			LOG.warn("Captcha was not found");
		}
	}

	/**
	 * Helper method, disables HTTP caching.
	 * 
	 * @param resp
	 *            response object to be modified
	 */
	private void setResponseHeaders(HttpServletResponse resp) {
		resp.setContentType("image/" + cage.getFormat());
		resp.setHeader("Cache-Control", "no-cache, no-store");
		resp.setHeader("Pragma", "no-cache");
		long time = System.currentTimeMillis();
		resp.setDateHeader("Last-Modified", time);
		resp.setDateHeader("Date", time);
		resp.setDateHeader("Expires", time);
	}

}
