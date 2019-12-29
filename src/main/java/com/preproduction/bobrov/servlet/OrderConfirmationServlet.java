package com.preproduction.bobrov.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.CartBean;
import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.constant.Message;
import com.preproduction.bobrov.constant.Path;
import com.preproduction.bobrov.entity.User;
import com.preproduction.bobrov.service.CartService;
import com.preproduction.bobrov.service.OrderService;
import com.preproduction.bobrov.util.MessageManager;

/**
 * Servlet that orders confirmation
 */
@WebServlet("/orderConfirmation")
public class OrderConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(OrderConfirmationServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		clearSessionAttributes(request);
		request.getRequestDispatcher(Path.ORDER_CONFIRMATION).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOG.info("Order confirmation request received");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(AttributeKey.USER);

		CartService cartService = (CartService) request.getServletContext().getAttribute(AttributeKey.CART_SERVICE);
		ServletContext context = request.getServletContext();
		OrderService orderService = (OrderService) context.getAttribute(AttributeKey.ORDER_SERVICE);
		MessageManager messageManager = (MessageManager) session.getAttribute(AttributeKey.MESSAGE_MANAGER);
		CartBean cart = (CartBean) request.getSession().getAttribute(AttributeKey.CART_BEAN);
		orderService.createOrder(user, cart);
		session.setAttribute(AttributeKey.SUCCESS_MESSAGE, messageManager.getMessage(Message.ORDER_CONFIRMED));
		cartService.clearCart(cart);
		response.sendRedirect("orderConfirmation");

	}

	/**
	 * Clears session attributes and sets them to request attributes
	 * 
	 * @param request
	 * @param session
	 */
	private void clearSessionAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession();
		request.setAttribute(AttributeKey.SUCCESS_MESSAGE, session.getAttribute(AttributeKey.SUCCESS_MESSAGE));
		session.removeAttribute(AttributeKey.SUCCESS_MESSAGE);
	}

}
