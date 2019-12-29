package com.preproduction.bobrov.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.CartBean;
import com.preproduction.bobrov.constant.OrderStatus;
import com.preproduction.bobrov.database.TransactionManager;
import com.preproduction.bobrov.database.Transactional;
import com.preproduction.bobrov.database.repository.OrderRepository;
import com.preproduction.bobrov.entity.Order;
import com.preproduction.bobrov.entity.OrderItem;
import com.preproduction.bobrov.entity.Product;
import com.preproduction.bobrov.entity.User;
import com.preproduction.bobrov.exception.DatabaseException;
/**
 * Provides operations with orders
 */
public class OrderService {

	private static final Logger LOG = Logger.getLogger(OrderService.class);

	private OrderRepository orderRepository;
	private TransactionManager transactionManager;

	public OrderService(OrderRepository orderRepository, TransactionManager transactionManager) {
		this.orderRepository = orderRepository;
		this.transactionManager = transactionManager;
	}

	/**
	 * Creates new order in database
	 * @param user
	 * @param cart
	 */
	public void createOrder(User user, CartBean cart) {
		transactionManager.doInTransaction(new Transactional<Void>() {
			@Override
			public Void doTransaction(Connection connection) {
				try {
					List<OrderItem> orderItems = new ArrayList<>();					
					for (Entry<Product, Integer> productEntry : cart.getProductMap().entrySet()) {
						Product product = productEntry.getKey();
						BigDecimal price = product.getPrice();
						int count = productEntry.getValue();
						orderItems.add(new OrderItem(product, count, price));
					}
					Order order = new Order();
					order.setUser(user);
					order.setStatus(OrderStatus.ACCEPTED);
					order.setDetail("Order has been accepted");
					order.setDate(new Date(System.currentTimeMillis()));
					order.setOrderItems(orderItems);
					orderRepository.addOrder(connection, order);
					return null;				

				} catch (SQLException e) {
					LOG.warn("Can't create new order", e);
					throw new DatabaseException("Can't create new order", e);
				}
			}
		});
	}
}
