package com.preproduction.bobrov.service;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.preproduction.bobrov.bean.SecurityBean;
import com.preproduction.bobrov.bean.SecurityBean.Constraint;
import com.preproduction.bobrov.constant.UserRole;
import com.preproduction.bobrov.entity.User;

public class SecurityServiceTest {

	private SecurityService securityService;

	@Before
	public void init() {
		Constraint constraint1 = new Constraint();
		constraint1.setUrlPattern("/path5");
		constraint1.getRole().addAll(Arrays.asList("admin", "user"));
		Constraint constraint2 = new Constraint();
		constraint2.setUrlPattern("/path10");
		constraint2.getRole().addAll(Arrays.asList("admin"));

		SecurityBean securityBean = new SecurityBean();
		securityBean.getConstraint().add(constraint1);
		securityBean.getConstraint().add(constraint2);
		securityService = new SecurityService(securityBean);
	}

	@Test
	public void testPageNotRestricted() {
		String actualPath = "/path5";
		Assert.assertTrue(securityService.isPathRestricted(actualPath));
	}
	
	@Test
	public void testPageRestricted() {
		String actualPath = "/unknownPath";
		Assert.assertFalse(securityService.isPathRestricted(actualPath));
	}
	
	@Test
	public void testUserAllowed() {
		String actualPath = "/path5";
		User user = new User();
		user.setRole(UserRole.ADMIN);
		Assert.assertTrue(securityService.isUserAllowed(actualPath, user));
	}
	
	@Test
	public void testUserNotAllowed() {
		String actualPath = "/path10";
		User user = new User();
		user.setRole(UserRole.USER);
		Assert.assertFalse(securityService.isUserAllowed(actualPath, user));
	}

}
