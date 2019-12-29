package com.preproduction.bobrov.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.exception.DatabaseException;

/**
 * Provides access to connection pool
 */
public class DatabaseConnector {

	private static final Logger LOG = Logger.getLogger(DatabaseConnector.class);

	private static DataSource dataSource;
	private static InitialContext initContext;

	public DatabaseConnector(String databaseName) throws DatabaseException {
		try {
			initContext = new InitialContext();
			Context context = (Context) initContext.lookup("java:comp/env");
			dataSource = (DataSource) context.lookup("jdbc/" + databaseName);
		} catch (NamingException e) {
			LOG.warn("Can't create data source", e);
			throw new DatabaseException("Can't create data source", e);
		}
	}

	/**
	 * Returns connection from connection pool
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public Connection getConnection() throws DatabaseException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			LOG.warn("Can't get connection", e);
			throw new DatabaseException("Can't get connection", e);
		}
	}

	public static void close(PreparedStatement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			LOG.warn("Can not close statement", e);
			throw new DatabaseException("Can not close statement", e);
		}
	}
	
	public static void close(ResultSet result) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException e) {
			LOG.warn("Can not close result set", e);
			throw new DatabaseException("Can not close result set", e);
		}
	}

}
