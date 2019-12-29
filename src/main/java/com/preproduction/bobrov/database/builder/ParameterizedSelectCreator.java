package com.preproduction.bobrov.database.builder;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;

/**
 * MySQL PreparedStatement creator 
 */
public class ParameterizedSelectCreator {	

	private Connection connection;
	private String query;
	private List<Object> parameters;

	private ParameterizedSelectCreator(Connection connection, String query, List<Object> parameters) {
		this.connection = connection;
		this.query = query;
		this.parameters = parameters;
	}

	/**
	 * Select queries builder
	 * 
	 */
	public static class Builder {

		private static final Logger LOG = Logger.getLogger(Builder.class);
		
		private List<String> columns = new ArrayList<>();
		private List<String> tables = new ArrayList<>();
		private List<String> conditions = new ArrayList<>();
		private List<String> sorts = new ArrayList<>();
		private Integer limit;
		private Integer offset;
		private boolean isDescending;

		private List<Object> parameters = new ArrayList<>();
		private Connection connection;

		public Builder(Connection connection) {
			this.connection = connection;
		}

		public ParameterizedSelectCreator build() {
			String query = buildParameriziedQuery();
			return new ParameterizedSelectCreator(connection, query, parameters);
		}

		/**
		 * Sets columns of the query
		 * 
		 * @param firstColumn
		 * @param otherColumns
		 * @return
		 */
		public Builder select(String firstColumn, String... otherColumns) {
			checkParametersForNull(firstColumn);
			columns.add(firstColumn);
			columns.addAll(Arrays.asList(otherColumns));
			return this;
		}

		/**
		 * Sets tables of the query
		 * 
		 * @param firstTable
		 * @param otherTables
		 * @return
		 */
		public Builder from(String firstTable, String... otherTables) {
			checkParametersForNull(firstTable);
			tables.add(firstTable);
			tables.addAll(Arrays.asList(otherTables));
			return this;
		}

		/**
		 * Sets 'equals' condition inside where statement of the query
		 * 
		 * @param column
		 * @param value
		 * @return
		 */
		public Builder whereEquals(String column, Object value) {
			checkParametersForNull(column, value);
			String sqlPart = column + "=?";

			conditions.add(sqlPart);
			parameters.add(value);
			return this;
		}

		/**
		 * Sets 'less or equals' condition inside where statement of the query
		 * 
		 * @param column
		 * @param value
		 * @return
		 */
		public Builder whereLessEquals(String column, Object value) {
			checkParametersForNull(column, value);
			String sqlPart = column + "<=?";
			conditions.add(sqlPart);
			parameters.add(value);
			return this;
		}

		/**
		 * Sets 'greater or equals' condition inside where statement of the
		 * query
		 * 
		 * @param column
		 * @param value
		 * @return
		 */
		public Builder whereGreaterEquals(String column, Object value) {
			checkParametersForNull(column, value);
			String sqlPart = column + ">=?";
			conditions.add(sqlPart);
			parameters.add(value);
			return this;
		}

		/**
		 * Sets 'in' condition inside where statement of the query
		 * 
		 * @param column
		 * @param values
		 * @return
		 */
		public Builder whereIn(String column, List<?> values) {
			checkParametersForNull(column, values);
			checkParametersForEmpty(values);
			StringBuilder sqlPart = new StringBuilder();

			Iterator<?> iterator = values.iterator();
			sqlPart.append(column).append(" IN (?");
			parameters.add(iterator.next());

			while (iterator.hasNext()) {
				sqlPart.append(",?");
				parameters.add(iterator.next());
			}
			sqlPart.append(")");
			conditions.add(sqlPart.toString());
			return this;
		}

		/**
		 * Sets columns by witch query result will be ordered
		 * 
		 * @param firstValue
		 * @param values
		 * @return
		 */
		public Builder orderBy(String firstValue, String... values) {
			checkParametersForNull(values);
			sorts.add(firstValue);
			sorts.addAll(Arrays.asList(values));
			return this;
		}

		/**
		 * Sets result limit
		 * 
		 * @param size
		 * @return
		 */
		public Builder setLimit(Integer size) {
			limit = size;
			return this;
		}

		/**
		 * Sets descending order of the sorting
		 * 
		 * @return
		 */
		public Builder setDescendingOrder() {
			isDescending = true;
			return this;
		}

		/**
		 * Sets result offset
		 * 
		 * @param offset
		 * @return
		 */
		public Builder setOffset(Integer offsetParameter) {
			offset = offsetParameter;
			return this;
		}

		private void checkParametersForNull(Object... parameters) {
			for (Object parameter : parameters) {
				if (parameter == null) {
					LOG.warn("Null sql buider parameter");
					throw new SelectCreatorException("Null sql builder parameter");
				}
			}
		}

		private void checkParametersForEmpty(List<?> parameters) {
			if (parameters.isEmpty()) {
				LOG.warn("List of parameters is empty");
				throw new SelectCreatorException("List of parameters is empty");
			}
		}

		/**
		 * Builds the query
		 * 
		 * @return
		 */
		private String buildParameriziedQuery() {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			sb.append(columns.get(0));
			for (int i = 1; i < columns.size(); i++) {
				sb.append(", " + columns.get(i));
			}
			sb.append(" FROM ");
			sb.append(tables.get(0));
			for (int i = 1; i < tables.size(); i++) {
				sb.append(", " + tables.get(i));
			}
			if (!conditions.isEmpty()) {
				sb.append(" WHERE ");
				sb.append(conditions.get(0));
				for (int i = 1; i < conditions.size(); i++) {
					sb.append(" AND " + conditions.get(i));
				}
			}
			if (!sorts.isEmpty()) {
				sb.append(" ORDER BY ");
				sb.append(sorts.get(0));
				for (int i = 1; i < sorts.size(); i++) {
					sb.append("," + sorts.get(i));
				}
				if (isDescending) {
					sb.append(" DESC");
				}
			}
			if (limit != null) {
				sb.append(" LIMIT ");
				if (offset != null) {
					sb.append(offset).append(", ");
				}
				sb.append(limit);
			}
			return sb.toString();
		}

	}

	/**
	 * Creates PreparedStatement object
	 * 
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement createStatement() throws SQLException {
		PreparedStatement statement = connection.prepareStatement(query);
		for (int i = 0; i < parameters.size(); i++) {
			statement.setObject(i + 1, parameters.get(i));
		}

		return statement;
	}

}
