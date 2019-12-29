package com.preproduction.bobrov.captcha;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.listener.ContextListener;

/**
 * Base class for captcha providers that stores captcha data into map
 */
public abstract class ContextCaptchaProvider implements CaptchaProvider {

	private static final Logger LOG = Logger.getLogger(ContextCaptchaProvider.class);
	
	/**
	 * Captcha storage
	 */
	protected Map<String, CaptchaBean> captchaMap = new ConcurrentHashMap<>();
	/**
	 * Captcha check time period in seconds
	 */
	private static final int CAPTCHA_CHECK_TIME = 60;

	public ContextCaptchaProvider() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		CaptchaRemover remover = new CaptchaRemover();
		executor.scheduleAtFixedRate(remover, CAPTCHA_CHECK_TIME, CAPTCHA_CHECK_TIME, TimeUnit.SECONDS);
		LOG.info("Captcha remover started");
	}

	/**
	 * Task for removing captchas that expired from captchaMap. Used for remove captchas if
	 * user has not submitted registartion.
	 */
	private class CaptchaRemover implements Runnable {

		@Override
		public void run() {
			Date currentDate = new Date();
			for (Entry<String, CaptchaBean> captcha : captchaMap.entrySet()) {
				if (currentDate.after(captcha.getValue().getExpirationDate())) {
					captchaMap.remove(captcha.getKey());
				}
			}
		}
	}

}
