package com.preproduction.bobrov.constant;

public enum UserRole {
	ADMIN("admin"), USER("user");
	
	private String name;
	
	private UserRole(String name) {
		this.name = name;
	}

	public static UserRole get(int id) {
		return values()[id];
	}
	
	public int getId() {
		return ordinal();
	}
	
	public String getName() {
		return name;
	}
	
	
}
