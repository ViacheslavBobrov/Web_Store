package com.preproduction.bobrov.entity;

import java.math.BigDecimal;

public class Product {

	private int id;
	private String name;
	private Category category;
	private Manufacturer manufacturer;
	private BigDecimal price;
	private String description;
	private String image;

	public Product() {
	}

	public Product(Product product) {
		id = product.id;
		name = product.name;
		category = new Category(product.category);
		manufacturer = new Manufacturer(product.manufacturer);
		price = product.price;
		description = product.description;
		image = product.image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {		
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}
		Product other = (Product) obj;
		return id == other.id;
	}

}
