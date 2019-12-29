package com.preproduction.bobrov.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.database.DatabaseConnector;
import com.preproduction.bobrov.entity.Category;
import com.preproduction.bobrov.exception.DatabaseException;

/**
 * Data access object for interacting with database. Operates category data
 *
 */
public class CategoryDAO {

	private static final String GET_BY_ID = "SELECT * FROM category WHERE id_category=?";
	private static final String GET_ALL = "SELECT * FROM category";
	
	/**
	 * Gets category from database by its id
	 * 
	 * @param id
	 *           id of the category
	 * @return Category object
	 * @throws SQLException
	 */
	public Category getById(Connection connection, int id) throws SQLException {
		ResultSet result = null;
		Category category = null;
		try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
			statement.setInt(1, id);
			result = statement.executeQuery();
			if (result.next()) {
				category = obtain(result);
			}
		} finally {
			DatabaseConnector.close(result);
		}
		return category;
	}
	
	public List<Category> getAll(Connection connection) throws SQLException {
		List<Category> categories = new ArrayList<>();
		ResultSet result = null;
		try (PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
			result = statement.executeQuery();
			while (result.next()) {
				categories.add(obtain(result));
			}
		} finally {
			DatabaseConnector.close(result);
		}
		return categories;
	}
	
	private Category obtain(ResultSet resultSet) throws SQLException {
		Category category = new Category();
		category.setId(resultSet.getInt("id_category"));
		category.setName(resultSet.getString("name"));
		return category;
	}
	
}
