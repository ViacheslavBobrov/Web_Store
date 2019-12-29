package com.preproduction.bobrov.entity;

import java.math.BigDecimal;

public final class OrderItem {

	private final Product product;
	private final int count;
	private final BigDecimal currentPrice;

	public OrderItem(Product product, int count, BigDecimal currentPrice) {
		this.product = new Product(product);
		this.count = count;
		this.currentPrice = currentPrice;
	}

	public Product getProduct() {
		return new Product(product);
	}

	public int getCount() {
		return count;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

}
