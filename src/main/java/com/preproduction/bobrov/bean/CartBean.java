package com.preproduction.bobrov.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.preproduction.bobrov.entity.Product;

public class CartBean {

	private Map<Product, Integer> productMap = new HashMap<>();

	public Map<Product, Integer> getProductMap() {
		return productMap;
	}

	public void setProductMap(Map<Product, Integer> productMap) {
		this.productMap = productMap;
	}
	
}
