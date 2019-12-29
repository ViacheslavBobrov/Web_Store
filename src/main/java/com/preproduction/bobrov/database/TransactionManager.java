package com.preproduction.bobrov.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.exception.DatabaseException;

/**
 * 
 * Provides database operations in transaction
 */
public class TransactionManager {

	private static final Logger LOG = Logger.getLogger(TransactionManager.class);

	private DatabaseConnector connector;

	public TransactionManager(DatabaseConnector connector) {
		this.connector = connector;
	}

	/**
	 * Executes operation in transaction
	 * @param operation operation to be executed
	 * @return transaction result
	 * @throws DatabaseException
	 */
	public <T> T doInTransaction(Transactional<T> operation) throws DatabaseException {
		Connection connection = connector.getConnection();
		T result = null;
		try {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); 
			result = operation.doTransaction(connection);
			connection.commit(); 
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.warn("Can't rollback transaction", e1);
				throw new DatabaseException("Can't rollback transaction", e1);
			}
		} finally { 
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.warn("Can't close connection", e);
				throw new DatabaseException("Can't close conncetion", e);
			}
		}
		return result;
	}
}
