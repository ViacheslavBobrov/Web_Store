package com.preproduction.bobrov.entity;

public class Category {

	private int id;

	private String name;

	public Category(Category category) {
		id = category.id;
		name = category.name;
	}

	public Category() {		
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

}
