package com.preproduction.bobrov.database.builder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.mysql.jdbc.PreparedStatement;

@RunWith(MockitoJUnitRunner.class)
public class ParameterizedSelectCreatorTest {

	@Mock
	Connection connection;
	@Mock
	PreparedStatement statement;
	
	@Before
	public void init() throws IOException, ServletException, SQLException {
		MockitoAnnotations.initMocks(ParameterizedSelectCreatorTest.class);
		Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
	}
	
	@Test
	public void testSQLQuery() throws SQLException {
		ParameterizedSelectCreator.Builder builder = new ParameterizedSelectCreator.Builder(connection);
		builder.select("column1", "column100")
		.from("table1", "table5")
		.whereEquals("column1", "value")
		.whereIn("column100", Arrays.asList(1, 2, 3, 4, 5));
		ParameterizedSelectCreator creator = builder.build();
		creator.createStatement();
		String expected = "SELECT column1, column100 FROM table1, table5 WHERE column1=? AND column100 IN (?,?,?,?,?)";
		Mockito.verify(connection).prepareStatement(Mockito.eq(expected));
		
	}
	
	@Test
	public void testSQLQueryWithOrder() throws SQLException {
		ParameterizedSelectCreator.Builder builder = new ParameterizedSelectCreator.Builder(connection);
		builder.select("column1", "column100")
		.from("table1", "table5")
		.whereEquals("column1", "value");		
		builder.orderBy("column1");
		ParameterizedSelectCreator creator = builder.build();
		creator.createStatement();
		String expected = "SELECT column1, column100 FROM table1, table5 WHERE column1=? ORDER BY column1";
		Mockito.verify(connection).prepareStatement(Mockito.eq(expected));		
	}
	
	@Test
	public void testSQLQueryWithOrderAndLimit() throws SQLException {
		ParameterizedSelectCreator.Builder builder = new ParameterizedSelectCreator.Builder(connection);
		builder.select("column1", "column100")
		.from("table1", "table5")
		.whereEquals("column1", "value");		
		builder.orderBy("column1")
		.setLimit(8)
		.setOffset(5);
		ParameterizedSelectCreator creator = builder.build();
		creator.createStatement();
		String expected = "SELECT column1, column100 FROM table1, table5 WHERE column1=? ORDER BY column1 LIMIT 5, 8";
		Mockito.verify(connection).prepareStatement(Mockito.eq(expected));		
	}
	
}
