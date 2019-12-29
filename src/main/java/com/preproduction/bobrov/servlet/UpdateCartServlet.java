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
import com.preproduction.bobrov.entity.Product;
import com.preproduction.bobrov.exception.UnknowCartActionException;
import com.preproduction.bobrov.service.CartService;
import com.preproduction.bobrov.service.OrderService;
import com.preproduction.bobrov.service.ProductService;

/**
 * Provides cart update actions
 */
@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ADD_PRODUCT_ACTION = "addProduct";
	private static final String REMOVE_PRODUCT_ACTION = "removeProduct";
	/**
	 * Command for removing all products of specified type
	 */
	private static final String REMOVE_ALL_PRODUCTS_ACTION = "removeAllProducts";

	private static final Logger LOG = Logger.getLogger(UpdateCartServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParameter = request.getParameter("productId");
		String action = request.getParameter("action");
		int productId = Integer.parseInt(idParameter);
		ProductService productService = (ProductService) request.getServletContext()
				.getAttribute(AttributeKey.PRODUCT_SERVICE);
		CartService cartService = (CartService) request.getServletContext().getAttribute(AttributeKey.CART_SERVICE);
		CartBean cart = (CartBean) request.getSession().getAttribute(AttributeKey.CART_BEAN);
		Product product = productService.getProductById(productId);

		switch (action) {
		case ADD_PRODUCT_ACTION:
			cartService.addProduct(cart, product);
			break;
		case REMOVE_PRODUCT_ACTION:
			cartService.removeProducts(cart, product, 1);
			break;
		case REMOVE_ALL_PRODUCTS_ACTION:
			cartService.removeProducts(cart, product, cartService.getCount(cart, product));
			break;
		default:
			LOG.warn("Unknown cart action");
			throw new UnknowCartActionException("Unknown cart action");
		}

		JSONObject answer = new JSONObject();
		answer.put("size", cartService.getSize(cart));
		answer.put("productCount", cartService.getCount(cart, product));
		answer.put("totalPrice", cartService.getTotalPrice(cart));
		response.setContentType("application/json");
		response.getWriter().write(answer.toJSONString());
	}

}
