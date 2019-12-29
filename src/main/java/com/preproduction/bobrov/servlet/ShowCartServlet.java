package com.preproduction.bobrov.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.constant.Path;
import com.preproduction.bobrov.service.CartService;

/**
 * Forwards user to the cart page
 */
public class ShowCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(Path.SHOW_CART).forward(request, response);
	}

}
