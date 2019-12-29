package com.preproduction.bobrov.database;

import java.sql.Connection;

import com.preproduction.bobrov.exception.DatabaseException;

/**
 * Interface for transactional operations
 * @param <T> Type of transaction result
 */
public interface Transactional<T> {
	
	/**
	 * Executes transaction operation
	 * 
	 * @param connection
	 * @return transaction result
	 * @throws DatabaseException if database error occured
	 */
	T doTransaction(Connection connection);
}
