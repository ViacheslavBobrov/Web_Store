package com.preproduction.bobrov.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.preproduction.bobrov.bean.ProductSearchBean;
import com.preproduction.bobrov.database.TransactionManager;
import com.preproduction.bobrov.database.Transactional;
import com.preproduction.bobrov.database.repository.ProductRepository;
import com.preproduction.bobrov.entity.Category;
import com.preproduction.bobrov.entity.Manufacturer;
import com.preproduction.bobrov.entity.Product;
import com.preproduction.bobrov.exception.DatabaseException;

public class ProductService {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	private ProductRepository productRepository;
	private TransactionManager transactionManager;

	public ProductService(ProductRepository productRepository, TransactionManager transactionManager) {
		this.productRepository = productRepository;
		this.transactionManager = transactionManager;
	}

	public Product getProductById(int id) {
		return transactionManager.doInTransaction(new Transactional<Product>() {
			@Override
			public Product doTransaction(Connection connection) {
				try {
					return productRepository.getById(connection, id);
				} catch (SQLException e) {
					LOG.warn("Can't get product by id", e);
					throw new DatabaseException("Can't get product by id", e);
				}
			}
		});
	}

	public List<Category> getAllCategories() {
		return transactionManager.doInTransaction(new Transactional<List<Category>>() {
			@Override
			public List<Category> doTransaction(Connection connection) {
				try {
					return productRepository.getAllCategories(connection);
				} catch (SQLException e) {
					LOG.warn("Can't get all categories", e);
					throw new DatabaseException("Can't get all categories", e);
				}
			}
		});
	}

	public List<Manufacturer> getAllManufacturers() {
		return transactionManager.doInTransaction(new Transactional<List<Manufacturer>>() {
			@Override
			public List<Manufacturer> doTransaction(Connection connection) {
				try {
					return productRepository.getAllManufacturers(connection);
				} catch (SQLException e) {
					LOG.warn("Can't get all manufacturers", e);
					throw new DatabaseException("Can't get all manufacturers", e);
				}
			}
		});
	}

	public List<Product> getFilteredProducts(ProductSearchBean bean) {
		return transactionManager.doInTransaction(new Transactional<List<Product>>() {
			@Override
			public List<Product> doTransaction(Connection connection) {
				try {
					return productRepository.getFilteredProducts(connection, bean);
				} catch (SQLException e) {
					LOG.warn("Can't get all filtered products", e);
					throw new DatabaseException("Can't get all filtered products", e);
				}
			}
		});
	}

	public int countFilteredProducts(ProductSearchBean bean) {
		return transactionManager.doInTransaction(new Transactional<Integer>() {
			@Override
			public Integer doTransaction(Connection connection) {
				try {
					return productRepository.getFilteredProductsCount(connection, bean);
				} catch (SQLException e) {
					LOG.warn("Can't get count of filtered products", e);
					throw new DatabaseException("Can't get count of filtered products", e);
				}
			}
		});
	}

}
