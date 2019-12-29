package com.preproduction.bobrov.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.preproduction.bobrov.bean.CartBean;
import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.service.CartService;

/**
 * Servlet implementation class GetCartInfoServlet
 */
@WebServlet("/getCartInfo")
public class GetCartInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final Logger LOG = Logger.getLogger(GetCartInfoServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOG.info("Get cart info request received");
		CartService cartService = (CartService) request.getServletContext().getAttribute(AttributeKey.CART_SERVICE);
		CartBean cart = (CartBean) request.getSession().getAttribute(AttributeKey.CART_BEAN);
		
		JSONObject answer = new JSONObject();
		answer.put("size", cartService.getSize(cart));
		answer.put("totalPrice", cartService.getTotalPrice(cart));
		response.setContentType("application/json");
		response.getWriter().write(answer.toJSONString());
		
	}


}
