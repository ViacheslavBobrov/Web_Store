package com.preproduction.bobrov.servlet;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.database.DatabaseConnector;
import com.preproduction.bobrov.database.TransactionManager;
import com.preproduction.bobrov.database.dao.UserDAO;
import com.preproduction.bobrov.entity.User;
import com.preproduction.bobrov.service.UserService;
import com.preproduction.bobrov.util.MessageManager;
import com.mysql.jdbc.Connection;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	@Mock
	private ServletContext context;
	@Mock
	private MessageManager messageManager;
	@Mock
	private User user;
	@Mock
	private UserService userService ;

	@Before
	public void init() throws IOException, ServletException {
		MockitoAnnotations.initMocks(LoginServletTest.class);
		Mockito.when(request.getSession()).thenReturn(session);		
		Mockito.when(messageManager.getMessage(Mockito.anyString())).thenReturn(Mockito.anyString());
		Mockito.when(session.getAttribute("messageManager")).thenReturn(messageManager);		
		Mockito.when(request.getServletContext()).thenReturn(context);
		Mockito.when(context.getAttribute("userService")).thenReturn(userService);
		
	}
	@Test
	public void testLoginWhenUserExists() throws ServletException, IOException  {
		LoginServlet servlet = new LoginServlet();		
		Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(new User());
		servlet.doPost(request, response);
		Mockito.verify(session).setAttribute(Mockito.eq("user"), Mockito.any());
	}
	
	@Test
	public void testLoginWhenUserNotExists() throws ServletException, IOException  {
		LoginServlet servlet = new LoginServlet();		
		Mockito.when(userService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		servlet.doPost(request, response);
		Mockito.verify(session).setAttribute(Mockito.eq("error"), Mockito.any());
	}
}
