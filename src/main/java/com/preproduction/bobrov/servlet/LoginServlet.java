package com.preproduction.bobrov.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.constant.Message;
import com.preproduction.bobrov.constant.Path;
import com.preproduction.bobrov.entity.User;
import com.preproduction.bobrov.service.UserService;
import com.preproduction.bobrov.util.MessageManager;

/**
 * Provide user login
 * 
 * @author Bobrov Vyacheslav
 *
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(LoginServlet.class);

	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		clearSessionAttributes(request);
		request.getRequestDispatcher(Path.LOGIN).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOG.info("Login request received");

		String email = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		UserService userService = (UserService) context.getAttribute(AttributeKey.USER_SERVICE);
		MessageManager messageManager = (MessageManager) session.getAttribute(AttributeKey.MESSAGE_MANAGER);
		User user = userService.login(email, password);
		String redirection = "login";
		if (user != null) {
			if (user.getAvatar() == null) {
				user.setAvatar(context.getInitParameter(AttributeKey.DEFAULT_AVATAR));
			}
			session.setAttribute(AttributeKey.USER, user);
			String unauthorizedRequest = request.getParameter(AttributeKey.UNAUTHORIZED_REQUEST);
			if (unauthorizedRequest != null) {
				System.out.println(unauthorizedRequest);
				redirection = unauthorizedRequest;
			}
		} else {
			session.setAttribute(AttributeKey.ERROR, messageManager.getMessage(Message.USER_NOT_EXISTS));
			session.setAttribute(EMAIL, email);
		}

		response.sendRedirect(redirection);
	}

	/**
	 * Clears session attributes and sets them to request attributes
	 * 
	 * @param request
	 * @param session
	 */
	private void clearSessionAttributes(HttpServletRequest request) {

		HttpSession session = request.getSession();
		request.setAttribute(AttributeKey.ERROR, session.getAttribute(AttributeKey.ERROR));
		request.setAttribute(EMAIL, session.getAttribute(EMAIL));
		session.removeAttribute(AttributeKey.ERROR);
		session.removeAttribute(EMAIL);

	}
}
