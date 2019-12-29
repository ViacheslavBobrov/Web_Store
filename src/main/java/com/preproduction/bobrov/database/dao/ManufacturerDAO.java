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
import com.preproduction.bobrov.entity.Manufacturer;
import com.preproduction.bobrov.exception.DatabaseException;

/**
 * Data access object for interacting with database. Operates manufacturer data
 * 
 */
public class ManufacturerDAO {

	private static final String GET_BY_ID = "SELECT * FROM manufacturer WHERE id_manufacturer=?";
	private static final String GET_ALL = "SELECT * FROM manufacturer";

	/**
	 * Gets manufacturer from database its id
	 * 
	 * @param id
	 *            id of the manufacturer
	 * @return Manufacturer object
	 * @throws SQLException
	 */
	public Manufacturer getById(Connection connection, int id) throws SQLException {
		ResultSet result = null;
		Manufacturer manufacturer = null;
		try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
			statement.setInt(1, id);
			result = statement.executeQuery();
			if (result.next()) {
				manufacturer = obtain(result);
			}
		} finally {
			DatabaseConnector.close(result);
		}
		return manufacturer;
	}

	public List<Manufacturer> getAll(Connection connection) throws SQLException {
		List<Manufacturer> manufacturers = new ArrayList<>();
		ResultSet result = null;
		try (PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
			result = statement.executeQuery();
			while (result.next()) {
				manufacturers.add(obtain(result));
			}
		} finally {
			DatabaseConnector.close(result);
		}
		return manufacturers;
	}
	
	private Manufacturer obtain(ResultSet resultSet) throws SQLException {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setId(resultSet.getInt("id_manufacturer"));
		manufacturer.setName(resultSet.getString("name"));
		return manufacturer;
	}
}
