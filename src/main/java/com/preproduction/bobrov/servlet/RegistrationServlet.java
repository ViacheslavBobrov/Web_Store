package com.preproduction.bobrov.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.bean.RegistrationFormBean;
import com.preproduction.bobrov.captcha.CaptchaProvider;
import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.constant.Message;
import com.preproduction.bobrov.constant.Path;
import com.preproduction.bobrov.constant.UserRole;
import com.preproduction.bobrov.entity.User;
import com.preproduction.bobrov.listener.ContextListener;
import com.preproduction.bobrov.service.AvatarService;
import com.preproduction.bobrov.service.UserService;
import com.preproduction.bobrov.util.CaptchaGenerator;
import com.preproduction.bobrov.util.MessageManager;
import com.preproduction.bobrov.validator.RegistrationFormValidator;

/**
 * Servlet that provides users registratins
 */

public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	private static final Logger LOG = Logger.getLogger(RegistrationServlet.class);
	private static final String NAME = "name";
	private static final String SURNAME = "surname";
	private static final String EMAIL = "email";
	private static final String FIRST_PASSWORD = "firstPassword";
	private static final String SECOND_PASSWORD = "secondPassword";
	private static final String DELIVERIES_CHECKED = "deliveriesChecked";
	private static final String CAPTCHA_VALUE = "captchaValue";
	private static final String AVATAR = "avatar";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		clearSessionAttributes(request, request.getSession());
		ServletContext context = request.getServletContext();
		CaptchaProvider provider = (CaptchaProvider) context.getAttribute(AttributeKey.CAPTCHA_PROVIDER);
		CaptchaBean captcha = createCaptcha(context);
		provider.putCaptcha(captcha, request, response);
		request.getRequestDispatcher(Path.REGISTARTION).forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.info("Registration request received");
		
		RegistrationFormBean registrationBean = obtainFields(request);

		ServletContext context = request.getServletContext();
		CaptchaProvider captchaProvider = (CaptchaProvider) context.getAttribute(AttributeKey.CAPTCHA_PROVIDER);
		RegistrationFormValidator validator = (RegistrationFormValidator) context
				.getAttribute(AttributeKey.REGISTRATION_VALIDATOR);
		MessageManager messageManager = (MessageManager) request.getSession().getAttribute(AttributeKey.MESSAGE_MANAGER);
		AvatarService avatarService = (AvatarService) context.getAttribute(AttributeKey.AVATAR_SERVICE);

		List<String> errors = validator.validate(registrationBean, captchaProvider.getCaptcha(request), messageManager);
		captchaProvider.removeCaptcha(request);

		UserService userService = (UserService) context.getAttribute(AttributeKey.USER_SERVICE);
		HttpSession session = request.getSession();
		if (errors.isEmpty()) {

			User user = obtainUser(registrationBean);
			user.setRole(UserRole.USER);

			if (!registrationBean.getAvatar().isEmpty()) {
				user.setAvatar(avatarService.generateAvatarName(registrationBean.getAvatar()));
			}

			if (userService.register(user)) {
				avatarService.loadAvatar(request, user.getAvatar());
				session.setAttribute(AttributeKey.SUCCESS_MESSAGE,
						messageManager.getMessage(Message.REGISTRATION_SUCCESS) + registrationBean.getName() + " "
								+ registrationBean.getSurname());
			} else {
				errors.add(messageManager.getMessage(Message.USER_EXISTS));
				setRegistrationFailedMessages(session, errors, registrationBean);
			}
		} else {
			setRegistrationFailedMessages(session, errors, registrationBean);
		}
		response.sendRedirect("registration");
	}

	private RegistrationFormBean obtainFields(HttpServletRequest request) throws IOException, ServletException {
		RegistrationFormBean registrationBean = new RegistrationFormBean();
		registrationBean.setName(request.getParameter(NAME));
		registrationBean.setSurname(request.getParameter(SURNAME));
		registrationBean.setEmail(request.getParameter(EMAIL));
		registrationBean.setFirstPassword(request.getParameter(FIRST_PASSWORD));
		registrationBean.setSecondPassword(request.getParameter(SECOND_PASSWORD));
		if (request.getParameterValues(DELIVERIES_CHECKED) != null) {
			registrationBean.setDeliveriesChecked(true);
		}
		registrationBean.setCaptchaValue(request.getParameter(CAPTCHA_VALUE));
		registrationBean.setAvatar(request.getPart(AVATAR).getSubmittedFileName());
		return registrationBean;
	}

	/**
	 * Clears session attributes and sets them to request attributes
	 * 
	 * @param request
	 * @param session
	 */
	private void clearSessionAttributes(HttpServletRequest request, HttpSession session) {
		request.setAttribute(AttributeKey.FORM_BEAN, session.getAttribute(AttributeKey.FORM_BEAN));
		request.setAttribute(AttributeKey.ERRORS, session.getAttribute(AttributeKey.ERRORS));
		request.setAttribute(AttributeKey.SUCCESS_MESSAGE, session.getAttribute(AttributeKey.SUCCESS_MESSAGE));
		session.removeAttribute(AttributeKey.FORM_BEAN);
		session.removeAttribute(AttributeKey.ERRORS);
		session.removeAttribute(AttributeKey.SUCCESS_MESSAGE);
	}

	/**
	 * Creates new captcha
	 * 
	 * @param context
	 * @return created captcha
	 */
	private CaptchaBean createCaptcha(ServletContext context) {
		CaptchaGenerator generator = (CaptchaGenerator) context.getAttribute(AttributeKey.CAPTCHA_GENERATOR);
		Integer captchaLifeTime = (Integer) context.getAttribute(AttributeKey.CAPTCHA_LIFETIME);
		return generator.create(captchaLifeTime);
	}

	private User obtainUser(RegistrationFormBean formBean) {
		User user = new User();
		user.setName(formBean.getName());
		user.setSurname(formBean.getSurname());
		user.setEmail(formBean.getEmail());
		user.setPassword(formBean.getFirstPassword());
		return user;
	}

	private void setRegistrationFailedMessages(HttpSession session, List<String> errors,
			RegistrationFormBean registrationBean) {
		LOG.info("Registration failed");
		session.setAttribute(AttributeKey.ERRORS, errors);
		session.setAttribute(AttributeKey.FORM_BEAN, registrationBean);
	}

}
