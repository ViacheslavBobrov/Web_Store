package com.preproduction.bobrov.database.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.preproduction.bobrov.entity.Order;

public class OrderDAO {

	private static final String INSERT_ORDER = "INSERT INTO orders(status, detail, date, id_user) VALUES(?, ?, ?, ?)";
	
	public int insert(Connection connection, Order order) throws SQLException {
		ResultSet resultSet = null;
		try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, order.getStatus().getId());
			statement.setString(2, order.getDetail());
			Timestamp timestamp = new Timestamp(order.getDate().getTime());
			statement.setTimestamp(3, timestamp);
			statement.setInt(4, order.getUser().getId());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			return resultSet.getInt(1);
		}
	}

}
