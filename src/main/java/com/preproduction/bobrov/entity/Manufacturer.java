package com.preproduction.bobrov.entity;

public class Manufacturer {

	private int id;

	private String name;

	public Manufacturer() {
	}

	public Manufacturer(Manufacturer manufacturer) {
		id = manufacturer.id;
		name = manufacturer.name;
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
