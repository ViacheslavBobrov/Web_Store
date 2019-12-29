package com.preproduction.bobrov.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.preproduction.bobrov.bean.CartBean;
import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.service.CartService;

@WebServlet("/clearCart")
public class ClearCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CartBean cart = (CartBean) request.getSession().getAttribute(AttributeKey.CART_BEAN);
		CartService cartService = (CartService) request.getServletContext().getAttribute(AttributeKey.CART_SERVICE);
		cartService.clearCart(cart);
		response.sendRedirect("showCart");
	}
}
