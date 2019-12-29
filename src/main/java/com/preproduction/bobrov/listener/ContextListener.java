package com.preproduction.bobrov.listener;

import java.io.File;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.SecurityBean;
import com.preproduction.bobrov.captcha.CaptchaProvider;
import com.preproduction.bobrov.captcha.CaptchaProviderFactory;
import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.database.DatabaseConnector;
import com.preproduction.bobrov.database.TransactionManager;
import com.preproduction.bobrov.database.dao.CategoryDAO;
import com.preproduction.bobrov.database.dao.ManufacturerDAO;
import com.preproduction.bobrov.database.dao.OrderDAO;
import com.preproduction.bobrov.database.dao.OrderItemDAO;
import com.preproduction.bobrov.database.dao.ProductDAO;
import com.preproduction.bobrov.database.dao.UserDAO;
import com.preproduction.bobrov.database.repository.OrderRepository;
import com.preproduction.bobrov.database.repository.ProductRepository;
import com.preproduction.bobrov.exception.FolderCreationException;
import com.preproduction.bobrov.exception.InitializationException;
import com.preproduction.bobrov.language.LanguageProvider;
import com.preproduction.bobrov.language.LanguageProviderFactory;
import com.preproduction.bobrov.service.AvatarService;
import com.preproduction.bobrov.service.CartService;
import com.preproduction.bobrov.service.OrderService;
import com.preproduction.bobrov.service.ProductService;
import com.preproduction.bobrov.service.SecurityService;
import com.preproduction.bobrov.service.UserService;
import com.preproduction.bobrov.util.CaptchaGenerator;
import com.preproduction.bobrov.util.SecurityJaxbUnmarshaller;
import com.preproduction.bobrov.util.MessageManager;
import com.preproduction.bobrov.validator.FilterSearchValidator;
import com.preproduction.bobrov.validator.RegistrationFormValidator;

/**
 * Inicializates application
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent contextEvent) {
		ServletContext context = contextEvent.getServletContext();
		setParameters(context);
		setCaptchaProvider(context);
		setCaptchaGenerator(context);
		setRegistrationFormValidator(context);
		TransactionManager manager = createTransactionManager(context);

		setUserService(context, manager);
		setAvatarService(context);
		setProductService(context, manager);
		setFilterSearchValidator(context);
		setOrderService(context, manager);
		setCartService(context);

		setLanguageProvider(context);
		
		setSecurityService(context);

		LOG.info("Application has been inizialized");
	}

	private void setParameters(ServletContext context) {
		String avatarFolderPath = context.getInitParameter(AttributeKey.IMAGE_FOLDER);
		File avatarFolder = new File(avatarFolderPath);
		if (!avatarFolder.exists() && !avatarFolder.mkdirs()) {
			LOG.warn("Error creating image folder");
			throw new FolderCreationException("Error creating image folder");
		}
		context.setAttribute(AttributeKey.IMAGE_FOLDER, context.getInitParameter(AttributeKey.IMAGE_FOLDER));
		String defaultAvatar = context.getInitParameter(AttributeKey.DEFAULT_AVATAR);
		context.setAttribute(AttributeKey.DEFAULT_AVATAR, defaultAvatar);
		String captchaLifeTime = context.getInitParameter(AttributeKey.CAPTCHA_LIFETIME);
		context.setAttribute(AttributeKey.CAPTCHA_LIFETIME, Integer.parseInt(captchaLifeTime));

		String languageCookieLifetime = context.getInitParameter(AttributeKey.LANGUAGE_COOKIE_LIFETIME);
		context.setAttribute(AttributeKey.LANGUAGE_COOKIE_LIFETIME, Integer.parseInt(languageCookieLifetime));

	}

	private void setCaptchaProvider(ServletContext context) {
		String captchaProviderType = context.getInitParameter(AttributeKey.CAPTCHA_PROVIDER_TYPE);
		CaptchaProviderFactory factory = new CaptchaProviderFactory();
		CaptchaProvider provider = factory.createProvider(captchaProviderType);
		context.setAttribute(AttributeKey.CAPTCHA_PROVIDER, provider);
		LOG.info("Captcha provider type '" + captchaProviderType + "' has been set");
	}

	private void setCaptchaGenerator(ServletContext context) {
		CaptchaGenerator generator = new CaptchaGenerator();
		context.setAttribute(AttributeKey.CAPTCHA_GENERATOR, generator);
	}

	private void setUserService(ServletContext context, TransactionManager manager) {
		UserDAO userDAO = new UserDAO();
		UserService userService = new UserService(manager, userDAO);
		context.setAttribute(AttributeKey.USER_SERVICE, userService);
	}

	private void setProductService(ServletContext context, TransactionManager manager) {
		CategoryDAO categoryDAO = new CategoryDAO();
		ManufacturerDAO manufacturerDAO = new ManufacturerDAO();
		ProductDAO productDAO = new ProductDAO();
		ProductRepository productRepository = new ProductRepository(categoryDAO, manufacturerDAO, productDAO);
		ProductService productService = new ProductService(productRepository, manager);
		context.setAttribute(AttributeKey.PRODUCT_SERVICE, productService);
	}

	private void setRegistrationFormValidator(ServletContext context) {
		RegistrationFormValidator validator = new RegistrationFormValidator();
		context.setAttribute(AttributeKey.REGISTRATION_VALIDATOR, validator);
	}

	private void setAvatarService(ServletContext context) {
		AvatarService avatarService = new AvatarService(context.getInitParameter(AttributeKey.IMAGE_FOLDER));
		context.setAttribute(AttributeKey.AVATAR_SERVICE, avatarService);
	}

	private TransactionManager createTransactionManager(ServletContext context) {
		String databasename = context.getInitParameter("databaseName");
		DatabaseConnector connector = new DatabaseConnector(databasename);
		return new TransactionManager(connector);
	}

	private void setFilterSearchValidator(ServletContext context) {
		FilterSearchValidator validator = new FilterSearchValidator();
		context.setAttribute(AttributeKey.SEARCH_FILTER_VALIDATOR, validator);
	}

	private void setOrderService(ServletContext context, TransactionManager manager) {
		OrderDAO orderDAO = new OrderDAO();
		OrderItemDAO orderItemDAO = new OrderItemDAO();
		OrderRepository orderRepository = new OrderRepository(orderDAO, orderItemDAO);
		OrderService orderService = new OrderService(orderRepository, manager);
		context.setAttribute(AttributeKey.ORDER_SERVICE, orderService);
	}

	private void setLanguageProvider(ServletContext context) {
		String languageStorage = context.getInitParameter(AttributeKey.LANGUAGE_STORAGE);
		LanguageProviderFactory factory = new LanguageProviderFactory();
		LanguageProvider languageProvider = factory.createProvider(languageStorage);
		context.setAttribute(AttributeKey.LANGUAGE_PROVIDER, languageProvider);
		
	}

	private void setCartService(ServletContext context) {
		CartService cartService = new CartService();
		context.setAttribute(AttributeKey.CART_SERVICE, cartService);
	}

	private void setSecurityService(ServletContext context) {
		SecurityJaxbUnmarshaller unmarshaller = new SecurityJaxbUnmarshaller();
		SecurityBean securityBean = null;
		try {
			securityBean = unmarshaller.unmarshall("security.xml");
		} catch (JAXBException e) {
			LOG.warn("Can't read security data");
			throw new InitializationException("Can't read security data");
		}

		SecurityService securityService = new SecurityService(securityBean);
		context.setAttribute(AttributeKey.SECURITY_SERVICE, securityService);		
	}
}
