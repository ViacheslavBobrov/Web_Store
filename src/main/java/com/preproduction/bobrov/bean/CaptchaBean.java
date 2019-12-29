package com.preproduction.bobrov.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Class represents entity that stores captcha data
 */
public class CaptchaBean implements Serializable{
	
	private static final long serialVersionUID = -2809664992980265227L;
	private String id;
	private String value;
	private Date expirationDate;	
	
	public CaptchaBean(String id, String value, Date expirationDate) {
		this.id = id;
		this.value = value;
		this.expirationDate = expirationDate;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}	
	
	
	
}
