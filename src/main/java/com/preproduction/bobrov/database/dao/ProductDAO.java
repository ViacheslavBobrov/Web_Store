package com.preproduction.bobrov.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.preproduction.bobrov.bean.ProductSearchBean;
import com.preproduction.bobrov.database.DatabaseConnector;
import com.preproduction.bobrov.database.builder.ParameterizedSelectCreator;
import com.preproduction.bobrov.entity.Category;
import com.preproduction.bobrov.entity.Manufacturer;
import com.preproduction.bobrov.entity.Product;

/**
 * Data access object for interacting with database. Operates product data
 * 
 */
public class ProductDAO {

	private static final String GET_BY_ID = "SELECT * FROM product WHERE id_product = ?";

	public Product getById(Connection connection, int id) throws SQLException {
		Product product = null;
		ResultSet result = null;
		try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
			statement.setInt(1, id);
			result = statement.executeQuery();
			if (result.next()) {
				product = obtainProduct(result);
			}
		} finally {
			DatabaseConnector.close(result);
		}
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
	public List<Product> getAllFiltered(Connection connection, ProductSearchBean bean) throws SQLException {
		List<Product> products = new ArrayList<>();
		ParameterizedSelectCreator.Builder builder = new ParameterizedSelectCreator.Builder(connection);
		builder.select("*").from("product");
		setConditions(bean, builder);
		setSelectedRange(bean, builder);
		ParameterizedSelectCreator creator = builder.build();
		try (PreparedStatement statement = creator.createStatement(); ResultSet result = statement.executeQuery()) {
			while (result.next()) {
				products.add(obtainProduct(result));
			}
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
	public int getFilteredCount(Connection connection, ProductSearchBean bean) throws SQLException {
		int amount = 0;

		ParameterizedSelectCreator.Builder builder = new ParameterizedSelectCreator.Builder(connection);
		builder.select("COUNT(*) AS amount").from("product");
		setConditions(bean, builder);
		ParameterizedSelectCreator creator = builder.build();
		try (PreparedStatement statement = creator.createStatement(); ResultSet result = statement.executeQuery()) {
			result.next();
			amount = result.getInt("amount");
		}
		return amount;
	}

	private void setConditions(ProductSearchBean bean, ParameterizedSelectCreator.Builder builder) {

		if (bean.getName() != null && !bean.getName().isEmpty()) {
			builder.whereEquals("name", bean.getName());
		}
		if (bean.getPriceFrom() != null) {
			builder.whereGreaterEquals("price", bean.getPriceFrom());
		}
		if (bean.getPriceTo() != null) {
			builder.whereLessEquals("price", bean.getPriceTo());
		}
		if (bean.getCategories() != null) {
			builder.whereIn("id_category", bean.getCategories());
		}
		if (bean.getManufacturers() != null) {
			builder.whereIn("id_manufacturer", bean.getManufacturers());
		}
		if (bean.getSortedBy() != null) {
			builder.orderBy(bean.getSortedBy());
			if (bean.isDescending()) {
				builder.setDescendingOrder();
			}
		}
	}

	private void setSelectedRange(ProductSearchBean bean, ParameterizedSelectCreator.Builder builder) {
		builder.setLimit(bean.getLimit());
		builder.setOffset(bean.getOffset());
	}

	private Product obtainProduct(ResultSet resultSet) throws SQLException {
		Product product = new Product();
		product.setName(resultSet.getString("name"));
		product.setPrice(resultSet.getBigDecimal("price"));
		product.setId(resultSet.getInt("id_product"));
		Category category = new Category();
		category.setId(resultSet.getInt("id_category"));
		product.setCategory(category);
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setId(resultSet.getInt("id_manufacturer"));
		product.setManufacturer(manufacturer);
		product.setDescription(resultSet.getString("description"));
		product.setImage(resultSet.getString("image"));
		return product;
	}
}
