package com.preproduction.bobrov.entity;

import java.io.Serializable;

import com.preproduction.bobrov.constant.UserRole;


public class User implements Serializable {

	private static final long serialVersionUID = -8729888451146603437L;

	private int id;
	
	private String name;

	private String surname;

	private String email;

	private String password;
	
	private String avatar;

	private boolean isDeliveries;
	
	private UserRole role;

	public User() {
	}

	public User(String name, String surname, String email, String password, boolean isDeliveries) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.isDeliveries = isDeliveries;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isDeliveries() {
		return isDeliveries;
	}

	public void setDeliveries(boolean isDeliveries) {
		this.isDeliveries = isDeliveries;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
}
