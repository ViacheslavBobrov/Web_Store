package com.preproduction.bobrov.listener;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.preproduction.bobrov.bean.CartBean;
import com.preproduction.bobrov.constant.AttributeKey;
import com.preproduction.bobrov.service.CartService;
import com.preproduction.bobrov.util.MessageManager;


@WebListener
public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		
		HttpSession session = event.getSession();
		session.setAttribute(AttributeKey.CART_BEAN, new CartBean());
		setMessageManager(session);
	}
	
	private void setMessageManager(HttpSession session) {
		
		MessageManager messageManager = new MessageManager();
		session.setAttribute(AttributeKey.MESSAGE_MANAGER, messageManager);
	}
	
	public void sessionDestroyed(HttpSessionEvent event) {

	}

}
