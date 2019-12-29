package com.preproduction.bobrov.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.preproduction.bobrov.bean.SecurityBean;
import com.preproduction.bobrov.bean.SecurityBean.Constraint;
import com.preproduction.bobrov.entity.User;

/**
 * Service for checking whether user is allowed to access specified page 
 */
public class SecurityService {

	private Map<String, List<String>> securityMap = new HashMap<String, List<String>>();

	public SecurityService(SecurityBean securityBean) {
		List<Constraint> securityConstraints = securityBean.getConstraint();
		for (Constraint constraint : securityConstraints) {
			securityMap.put(constraint.getUrlPattern(), constraint.getRole());
		}
	}
	
	/**
	 * Returns true if access to the path is allowed only for authorized users 
	 */
	public boolean isPathRestricted(String path) {
		return securityMap.containsKey(path);
	}
	
	/**
	 * Returns true if user can access the specified path 
	 */
	public boolean isUserAllowed(String path, User user) {
		List<String> roles = securityMap.get(path);
		if(roles != null) {
			return roles.contains(user.getRole().getName());
		}
		return true;
	}

}
