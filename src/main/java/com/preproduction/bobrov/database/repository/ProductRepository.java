package com.preproduction.bobrov.database.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.preproduction.bobrov.bean.ProductSearchBean;
import com.preproduction.bobrov.database.dao.CategoryDAO;
import com.preproduction.bobrov.database.dao.ManufacturerDAO;
import com.preproduction.bobrov.database.dao.ProductDAO;
import com.preproduction.bobrov.entity.Category;
import com.preproduction.bobrov.entity.Manufacturer;
import com.preproduction.bobrov.entity.Product;

/**
 * Repository for products
 */
public class ProductRepository {

	private CategoryDAO categoryDAO;
	private ManufacturerDAO manufacturerDAO;
	private ProductDAO productDAO;

	public ProductRepository(CategoryDAO categoryDAO, ManufacturerDAO manufacturerDAO, ProductDAO productDAO) {
		this.categoryDAO = categoryDAO;
		this.manufacturerDAO = manufacturerDAO;
		this.productDAO = productDAO;
	}

	public Product getById(Connection connection, int id) throws SQLException {
		Product product = productDAO.getById(connection, id);
		product.setCategory(categoryDAO.getById(connection, product.getCategory().getId()));
		product.setManufacturer(manufacturerDAO.getById(connection, product.getManufacturer().getId()));
		return product;
	}

	/**
	 * Returns list of products filtered by parameters that are set in bean
	 * 
	 * @param connection
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public List<Product> getFilteredProducts(Connection connection, ProductSearchBean bean) throws SQLException {
		List<Product> products = productDAO.getAllFiltered(connection, bean);
		for (Product product : products) {
			product.setCategory(categoryDAO.getById(connection, product.getCategory().getId()));
			product.setManufacturer(manufacturerDAO.getById(connection, product.getManufacturer().getId()));
		}
		return products;
	}

	/**
	 * Returns number of products filtered by parameters that are set in bean
	 * 
	 * @param connection
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int getFilteredProductsCount(Connection connection, ProductSearchBean bean) throws SQLException {
		return productDAO.getFilteredCount(connection, bean);
	}

	/**
	 * Returns list of all categories
	 * 
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public List<Category> getAllCategories(Connection connection) throws SQLException {
		return categoryDAO.getAll(connection);
	}

	/**
	 * Returns list of all manufacturers
	 * 
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public List<Manufacturer> getAllManufacturers(Connection connection) throws SQLException {
		return manufacturerDAO.getAll(connection);
	}

}
