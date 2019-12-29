package com.preproduction.bobrov.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.CartBean;
import com.preproduction.bobrov.entity.Product;

/**
 * Represents shop cart. Allows user to store products
 */
public class CartService {

	private static final Logger LOG = Logger.getLogger(CartService.class);

	/**
	 * Adds new product to the cart
	 */
	public void addProduct(CartBean cart, Product product) {
		Map<Product, Integer> productMap = cart.getProductMap();
		if (productMap.containsKey(product)) {
			int count = productMap.get(product);
			productMap.put(product, ++count);
		} else {
			productMap.put(product, 1);
		}
	}

	/**
	 * Removes products from cart
	 * 
	 * @param product
	 * @param number
	 *            number of products to remove
	 */
	public void removeProducts(CartBean cart, Product product, int number) {
		Map<Product, Integer> productMap = cart.getProductMap();
		Integer count = productMap.get(product);
		if (count != null) {
			if (number > count || number < 1) {
				LOG.warn("Not valid number of products");
				throw new IllegalArgumentException("Not valid number of products");
			}
			if (count - number <= 0) {
				productMap.remove(product);
			} else {
				productMap.put(product, count - number);
			}
		}
	}

	/**
	 * Returns number of all products in cart
	 */
	public int getSize(CartBean cart) {
		Map<Product, Integer> productMap = cart.getProductMap();
		int size = 0;
		for (Integer count : productMap.values()) {
			size += count;
		}
		return size;
	}

	public int getCount(CartBean cart, Product product) {
		Map<Product, Integer> productMap = cart.getProductMap();
		Integer count = productMap.get(product);
		return (count != null) ? count : 0;
	}

	/**
	 * Returns total price of all products
	 * 
	 * @return
	 */
	public BigDecimal getTotalPrice(CartBean cart) {
		Map<Product, Integer> productMap = cart.getProductMap();
		BigDecimal totalPrice = new BigDecimal(0);
		for (Entry<Product, Integer> productEntry : productMap.entrySet()) {
			BigDecimal productPrice = productEntry.getKey().getPrice();
			BigDecimal productCount = new BigDecimal(productEntry.getValue());
			totalPrice = totalPrice.add(productPrice.multiply(productCount));
		}
		return totalPrice;
	}

	public void clearCart(CartBean cart) {
		Map<Product, Integer> productMap = cart.getProductMap();
		productMap.clear();
	}
}
