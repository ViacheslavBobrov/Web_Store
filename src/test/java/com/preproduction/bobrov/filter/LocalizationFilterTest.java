package com.preproduction.bobrov.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.language.LanguageProvider;
import com.preproduction.bobrov.servlet.LoginServletTest;
import com.preproduction.bobrov.util.MessageManager;

@RunWith(MockitoJUnitRunner.class)
public class LocalizationFilterTest {

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private FilterChain chain;
	@Mock
	private LanguageProvider provider;
	@Mock
	private FilterConfig config;
	@Mock
	private ServletContext context;
	@Mock
	private HttpSession session;
	
	@Before
	public void init() throws IOException, ServletException {
		MockitoAnnotations.initMocks(LoginServletTest.class);
		Mockito.when(config.getInitParameter(AttributeKey.ACCEPTED_LANGUAGES)).thenReturn("en ru");
		Mockito.when(config.getInitParameter(AttributeKey.DEFAULT_LANGUAGE)).thenReturn("2");
		Mockito.when(context.getAttribute(AttributeKey.LANGUAGE_PROVIDER)).thenReturn(provider);
		Mockito.when(config.getServletContext()).thenReturn(context);
		Mockito.when(session.getAttribute(AttributeKey.MESSAGE_MANAGER)).thenReturn(new MessageManager());
		Mockito.when(request.getSession()).thenReturn(session);
	}
	
	@Test
	public void testDoFilterWhenLanguageInParameter() throws IOException, ServletException {
		LocalizationFilter filter = new LocalizationFilter();
		filter.init(config);
		Locale locale = new Locale("en");
		
		Mockito.when(request.getParameter(AttributeKey.LANGUAGE_PARAMETER)).thenReturn("en");
		
		filter.doFilter(request, response, chain);
		
		Mockito.verify(provider).setLanguage(Mockito.eq(locale), Mockito.any(), Mockito.any());
	}
	@Test
	public void testDoFilterWhenLanguageInProvider() throws IOException, ServletException {
		LocalizationFilter filter = new LocalizationFilter();
		filter.init(config);
		Locale locale = new Locale("en");
		
		Mockito.when(request.getParameter(AttributeKey.LANGUAGE_PARAMETER)).thenReturn("someLanguage");		
		Mockito.when(provider.getLanguage(request)).thenReturn(locale);
		
		filter.doFilter(request, response, chain);		
		Mockito.verify(provider).setLanguage(Mockito.eq(locale), Mockito.any(), Mockito.any());
	}
	
	@Test
	public void testDoFilterWhenLanguageInRequest() throws IOException, ServletException {
		LocalizationFilter filter = new LocalizationFilter();
		filter.init(config);
		Locale locale = new Locale("en");
		
		Mockito.when(request.getParameter(AttributeKey.LANGUAGE_PARAMETER)).thenReturn("someLanguage");		
		Mockito.when(provider.getLanguage(request)).thenReturn(null);
		Mockito.when(request.getLocales()).thenReturn(Collections.enumeration(Arrays.asList(locale)));
		
		filter.doFilter(request, response, chain);		
		Mockito.verify(provider).setLanguage(Mockito.eq(locale), Mockito.any(), Mockito.any());
	}
	
	@Test
	public void testDoFilterWhenDefaultLanguage() throws IOException, ServletException {
		LocalizationFilter filter = new LocalizationFilter();
		filter.init(config);
		Locale defaultLocale = new Locale("ru");
		
		Mockito.when(request.getParameter(AttributeKey.LANGUAGE_PARAMETER)).thenReturn("someLanguage");		
		Mockito.when(provider.getLanguage(request)).thenReturn(null);
		Mockito.when(request.getLocales()).thenReturn(Collections.emptyEnumeration());
		
		filter.doFilter(request, response, chain);		
		Mockito.verify(provider).setLanguage(Mockito.eq(defaultLocale), Mockito.any(), Mockito.any());
	}

}
