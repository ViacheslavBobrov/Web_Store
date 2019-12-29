package com.preproduction.bobrov.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "constraint" })
@XmlRootElement(name = "security")
public class SecurityBean {

	@XmlElement(required = true)
	protected List<SecurityBean.Constraint> constraint;

	public List<SecurityBean.Constraint> getConstraint() {
		if (constraint == null) {
			constraint = new ArrayList<SecurityBean.Constraint>();
		}
		return this.constraint;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "urlPattern", "role" })
	public static class Constraint {

		@XmlElement(name = "url-pattern", required = true)
		protected String urlPattern;
		@XmlElement(required = true)
		protected List<String> role = new ArrayList<String>();

		public String getUrlPattern() {
			return urlPattern;
		}

		public void setUrlPattern(String value) {
			this.urlPattern = value;
		}

		public List<String> getRole() {
			return this.role;
		}

	}

}
