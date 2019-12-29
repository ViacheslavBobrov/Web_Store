package com.preproduction.bobrov.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.preproduction.bobrov.bean.CaptchaBean;
import com.preproduction.bobrov.captcha.CaptchaProvider;
import com.preproduction.bobrov.database.DatabaseConnector;
import com.preproduction.bobrov.database.TransactionManager;
import com.preproduction.bobrov.database.dao.UserDAO;
import com.preproduction.bobrov.entity.User;
import com.preproduction.bobrov.service.AvatarService;
import com.preproduction.bobrov.service.UserService;
import com.preproduction.bobrov.util.MessageManager;
import com.preproduction.bobrov.validator.RegistrationFormValidator;
import com.mysql.jdbc.Connection;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServletTest {

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private ServletContext context;
	@Mock
	private UserDAO userDAO;
	@Mock
	private DatabaseConnector connector;
	
	private UserService userService ;
	@Mock
	private UserService generator;
	@Mock
	private CaptchaProvider provider;
	@Mock
	private HttpSession session;
	@Mock
	private Part part;
	@Mock
	private Connection connection;
	@Mock
	private AvatarService avatarService;
	@Mock
	private MessageManager messageManager = new MessageManager();
	

	private RegistrationFormValidator validator = new RegistrationFormValidator();

	@Before
	public void init() throws IOException, ServletException {
		MockitoAnnotations.initMocks(RegistrationServletTest.class);
		messageManager.setLocale(new Locale("en"));
		Mockito.when(context.getAttribute("captchaGenerator")).thenReturn(generator);
		Mockito.when(context.getAttribute("captchaLifeTime")).thenReturn(60);		
		Mockito.when(context.getAttribute("registrationValidator")).thenReturn(validator);
		Mockito.when(context.getAttribute("captchaProvider")).thenReturn(provider);
		Mockito.when(session.getAttribute("messageManager")).thenReturn(messageManager);
		Mockito.when(context.getAttribute("avatarService")).thenReturn(avatarService);
		Mockito.when(request.getServletContext()).thenReturn(context);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getPart("avatar")).thenReturn(part);		
		Mockito.when(connector.getConnection()).thenReturn(connection);
		userService = new UserService(new TransactionManager(connector), userDAO);
		Mockito.when(context.getAttribute("userService")).thenReturn(userService);
	}

	@Test
	public void testWhenFileldsValid() throws ServletException, IOException, SQLException {
		RegistrationServlet servlet = new RegistrationServlet();
		
		CaptchaBean captcha = new CaptchaBean("", "1234", getCaptchaExpirationDate(60));
		Mockito.when(provider.getCaptcha(request)).thenReturn(captcha);
		fillRequest();
		Mockito.when(part.getSubmittedFileName()).thenReturn("");
		servlet.doPost(request, response);
		Mockito.verify(userDAO, Mockito.times(1)).insert(Mockito.any(), Mockito.any());
		
	}
	
	@Test
	public void testWhenCaptchaExpired() throws ServletException, IOException, SQLException {
		RegistrationServlet servlet = new RegistrationServlet();
		
		CaptchaBean captcha = new CaptchaBean("", "1234", getCaptchaExpirationDate(0));
		Mockito.when(provider.getCaptcha(request)).thenReturn(captcha);		
		servlet.doPost(request, response);
		Mockito.verify(userDAO, Mockito.times(0)).insert(Mockito.any(), Mockito.any());
		
	}	
	@Test
	public void testWhenUserAlreadyExists() throws ServletException, IOException, SQLException {
		RegistrationServlet servlet = new RegistrationServlet();
		User user = new User();
		user.setEmail("user@gmail.com");
		Mockito.when(userDAO.getByEmail(Mockito.any(), Mockito.any())).thenReturn(new User());		
		CaptchaBean captcha = new CaptchaBean("", "1234", getCaptchaExpirationDate(60));
		Mockito.when(provider.getCaptcha(request)).thenReturn(captcha);
		fillRequest();
		Mockito.when(part.getSubmittedFileName()).thenReturn("");
		servlet.doPost(request, response);
		
		Mockito.verify(userDAO, Mockito.times(0)).insert(Mockito.any(), Mockito.any());
	}	
	@Test
	public void testWhenNotValidAvatarFormat() throws ServletException, IOException, SQLException {
		RegistrationServlet servlet = new RegistrationServlet();				
		CaptchaBean captcha = new CaptchaBean("", "1234", getCaptchaExpirationDate(60));
		Mockito.when(provider.getCaptcha(request)).thenReturn(captcha);
		fillRequest();
		Mockito.when(part.getSubmittedFileName()).thenReturn("saf.fewfw");
		servlet.doPost(request, response);
		
		Mockito.verify(userDAO, Mockito.times(0)).insert(Mockito.any(), Mockito.any());
	}	
	private void fillRequest() {
		Mockito.when(request.getParameter("captchaValue")).thenReturn("1234");
		Mockito.when(request.getParameter("name")).thenReturn("User");
		Mockito.when(request.getParameter("surname")).thenReturn("User");
		Mockito.when(request.getParameter("email")).thenReturn("user@gmail.com");
		Mockito.when(request.getParameter("firstPassword")).thenReturn("55555");
		Mockito.when(request.getParameter("secondPassword")).thenReturn("55555");
	}
	
	
	private Date getCaptchaExpirationDate(int lifeTime) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(new Date()); 
		calendar.add(Calendar.SECOND, lifeTime);		 
		return calendar.getTime();
	}

}
