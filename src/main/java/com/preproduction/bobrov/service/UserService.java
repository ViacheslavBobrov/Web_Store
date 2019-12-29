package com.preproduction.bobrov.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.database.DatabaseConnector;
import com.preproduction.bobrov.database.TransactionManager;
import com.preproduction.bobrov.database.Transactional;
import com.preproduction.bobrov.database.dao.UserDAO;
import com.preproduction.bobrov.entity.User;
import com.preproduction.bobrov.exception.DatabaseException;

/**
 * Provides operations with user data
 */
public class UserService {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	private UserDAO userDAO;
	private TransactionManager transactionManager;
 
	public UserService(TransactionManager transactionManager, UserDAO userDAO) {
		this.transactionManager = transactionManager;
		this.userDAO = userDAO;
	}
	
	/**
	 * Registrates user in database, if user with such email does not exist yet
	 * @param user
	 * @return true if user was registrated
	 */
	public boolean register(User user) {
		return transactionManager.doInTransaction(new Transactional<Boolean>() {
			@Override
			public Boolean doTransaction(Connection connection) {
				try {
					if (userDAO.getByEmail(connection, user.getEmail()) != null) {
						return false;
					}
					userDAO.insert(connection, user);
				} catch (SQLException e) {
					LOG.warn("Can't register user", e);
					throw new DatabaseException("Can't register user", e);
				}
				return true;
			}
		});
	}

	/**
	 * Logins user if user with such email exists and passwords are the same
	 * @param email
	 * @param password
	 * @return user if user with such email exists and passwords are the same
	 */
	public User login(String email, String password) {
		return transactionManager.doInTransaction(new Transactional<User>() {
			@Override
			public User doTransaction(Connection connection) {

				try {
					User user = userDAO.getByEmail(connection, email);
					if ((user != null) && user.getPassword().equals(password)) {
						return user;
					}
					return null;

				} catch (SQLException e) {
					LOG.warn("Can't login user", e);
					throw new DatabaseException("Can't login user", e);
				}

			}
		});
	}

	/**
	 * Returns user by email
	 * @param email
	 * @return
	 * @throws DatabaseException
	 */
	public User getByEmail(String email) throws DatabaseException {
		return transactionManager.doInTransaction(new Transactional<User>() {
			@Override
			public User doTransaction(Connection connection) {
				try {
					return userDAO.getByEmail(connection, email);
				} catch (SQLException e) {
					LOG.warn("Can't obtain user by email", e);
					throw new DatabaseException("Can't obtain user by email", e);
				}
			}
		});
	}
}
