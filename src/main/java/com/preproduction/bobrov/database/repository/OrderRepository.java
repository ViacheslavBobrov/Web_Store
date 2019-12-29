package com.preproduction.bobrov.database.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.preproduction.bobrov.database.dao.OrderDAO;
import com.preproduction.bobrov.database.dao.OrderItemDAO;
import com.preproduction.bobrov.entity.Order;

/**
 * Repository for orders
 */
public class OrderRepository {

	private OrderDAO orderDAO;
	private OrderItemDAO orderItemDAO;

	public OrderRepository(OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
		this.orderDAO = orderDAO;
		this.orderItemDAO = orderItemDAO;
	}
	
	public void addOrder(Connection connection, Order order) throws SQLException {
		int orderId = orderDAO.insert(connection, order);
		orderItemDAO.insertAllByOrderId(connection, orderId, order.getOrderItems());
	}

}
