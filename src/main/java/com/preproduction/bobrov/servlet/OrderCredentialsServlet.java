package com.preproduction.bobrov.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.constant.Path;
import com.preproduction.bobrov.entity.User;

/**
 * Servlet that recieves user order information
 */
@WebServlet("/orderCredentials")
public class OrderCredentialsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(OrderCredentialsServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(Path.ORDER_CREDENTIALS).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.info("Order credentials request received");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(AttributeKey.USER);

		String cardId = request.getParameter("cardId");
		String address = request.getParameter("address");
		request.setAttribute(AttributeKey.CARD_ID, cardId);
		request.setAttribute(AttributeKey.ADDRESS, address);
		request.getRequestDispatcher(Path.ORDER_CONFIRMATION).forward(request, response);
	}

}
