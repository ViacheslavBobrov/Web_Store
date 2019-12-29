package com.preproduction.bobrov.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.constant.UserRole;
import com.preproduction.bobrov.database.DatabaseConnector;
import com.preproduction.bobrov.entity.User;


/**
 * Data access object for interacting with database. Operates user data
 */
public class UserDAO {

	private static final String GET_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
	private static final String INSERT_USER = "INSERT INTO user(name, surname, email, password, is_deliveries, avatar, role) VALUES(?, ?, ?, ?, ?, ?, ?)";

	/**
	 * Gets user from database by his or her email
	 * 
	 * @param email
	 *            email of the user
	 * @return User object
	 * @throws SQLException
	 */
	public User getByEmail(Connection connection, String email) throws SQLException {
		ResultSet result = null;
		User user = null;
		try (PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL)) {
			statement.setString(1, email);
			result = statement.executeQuery();
			if (result.next()) {
				user = obtain(result);
			}
		} finally {
			DatabaseConnector.close(result);
		}
		return user;
	}

	/**
	 * Inserts new user to database
	 * 
	 * @param connection
	 * @param user
	 *            user to be inserted
	 * @throws SQLException
	 */
	public void insert(Connection connection, User user) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getSurname());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setBoolean(5, user.isDeliveries());
			statement.setString(6, user.getAvatar());
			statement.setInt(7, user.getRole().ordinal());
			statement.executeUpdate();
		}
	}

	/**
	 * Obtains User object from ResultSet
	 * 
	 * @param result
	 * @return obtained user
	 * @throws SQLException
	 */
	private User obtain(ResultSet result) throws SQLException {
		User user = new User();
		user.setId(result.getInt("id_user"));
		user.setName(result.getString("name"));
		user.setSurname(result.getString("surname"));
		user.setEmail(result.getString("email"));
		user.setPassword(result.getString("password"));
		user.setDeliveries(result.getBoolean("is_deliveries"));
		user.setAvatar(result.getString("avatar"));
		UserRole role = UserRole.get(Integer.parseInt(result.getString("role")));
		user.setRole(role);
		return user;
	}

}
