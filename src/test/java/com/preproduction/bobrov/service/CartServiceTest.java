package com.preproduction.bobrov.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.preproduction.bobrov.bean.CartBean;
import com.preproduction.bobrov.entity.Product;

public class CartServiceTest {

	@Test
	public void testGetTotalPrice() {
		CartService cartService = new CartService();
		CartBean cartBean = new CartBean();
		Product product1 = new Product();
		product1.setId(1);
		product1.setPrice(new BigDecimal(5));
		Product product2 = new Product();
		product2.setId(2);
		product2.setPrice(new BigDecimal(10));
		for (int i = 0; i < 5; i++) {
			cartService.addProduct(cartBean, product1);
			cartService.addProduct(cartBean, product2);
		}
		BigDecimal expectedValue = new BigDecimal(75);
		assertTrue(expectedValue.equals(cartService.getTotalPrice(cartBean)));
	}

	@Test
	public void testRemoveProduct() {
		CartService cartService = new CartService();
		CartBean cartBean = new CartBean();
		Product product = new Product();
		product.setId(1);		
		for (int i = 0; i < 10; i++) {
			cartService.addProduct(cartBean, product);
		}
		cartService.removeProducts(cartBean, product, 5);
		assertTrue(5 == cartService.getCount(cartBean, product));
	}
	
	@Test
	public void testGetSize() {
		CartService cartService = new CartService();
		CartBean cartBean = new CartBean();
		Product product = new Product();
		int expectedSize = 10;
		product.setId(1);		
		for (int i = 0; i < expectedSize; i++) {
			cartService.addProduct(cartBean, product);
		}
		assertTrue(expectedSize == cartService.getSize(cartBean));
	}
	
	@Test
	public void testGetCount() {
		CartService cartService = new CartService();
		CartBean cartBean = new CartBean();
		Product product = new Product();
		int expectedSize = 7;
		product.setId(1);		
		for (int i = 0; i < expectedSize; i++) {
			cartService.addProduct(cartBean, product);
		}
		assertTrue(expectedSize == cartService.getCount(cartBean, product));
	}

}
