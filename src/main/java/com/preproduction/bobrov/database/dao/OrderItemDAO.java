package com.preproduction.bobrov.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.preproduction.bobrov.entity.OrderItem;
import com.preproduction.bobrov.entity.Product;

public class OrderItemDAO {

	private static final String INSERT_ORDER_ITEM = "INSERT INTO order_item(id_order, id_product, count, price) VALUES (?, ?, ?, ?)";

	public void insertAllByOrderId(Connection connection, int orderId, List<OrderItem> orderItems) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_ITEM)) {
			for (OrderItem item : orderItems) {
				Product product = item.getProduct();
				statement.setInt(1, orderId);
				statement.setInt(2, product.getId());
				statement.setInt(3, item.getCount());
				statement.setBigDecimal(4, product.getPrice());
				statement.addBatch();
			}
			statement.executeBatch();
		}
	}

}
