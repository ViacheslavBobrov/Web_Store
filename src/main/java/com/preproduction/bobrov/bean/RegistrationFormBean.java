package com.preproduction.bobrov.bean;

import java.io.Serializable;

/**
 * Class represents entity that stores data from registration form fields
 */
public class RegistrationFormBean implements Serializable{

	private static final long serialVersionUID = 5377222485099389522L;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String firstPassword;
	
	private String secondPassword;
	
	private boolean isDeliveriesChecked;
	
	private String captchaValue;
	
	private String avatar;
	
		

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

	public String getFirstPassword() {
		return firstPassword;
	}

	public void setFirstPassword(String firstPassword) {
		this.firstPassword = firstPassword;
	}

	public String getSecondPassword() {
		return secondPassword;
	}

	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}

	public boolean isDeliveriesChecked() {
		return isDeliveriesChecked;
	}

	public void setDeliveriesChecked(boolean isDeliveriesChecked) {
		this.isDeliveriesChecked = isDeliveriesChecked;
	}

	public String getCaptchaValue() {
		return captchaValue;
	}

	public void setCaptchaValue(String captchaValue) {
		this.captchaValue = captchaValue;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
	
}
