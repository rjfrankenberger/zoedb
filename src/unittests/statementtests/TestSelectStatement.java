package unittests.statementtests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONObject;

import zoedb.SQLStatement;
import zoedb.SelectStatement;
import zoedb.result.Result;

public class TestSelectStatement extends TestCase {

	public void testCreateWithColumnList() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		ArrayList<String> columnList = new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		ArrayList<String> orderByList = new ArrayList<String>();
		orderByList.add("city ASC");
		orderByList.add("state DESC");
		orderByList.add("country");
		select.addClause("select", (List) columnList);
		select.addClause("where", "Name='bobby'");
		select.addClause("order by", orderByList);
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM sakila.TestTable " +
					 "WHERE Name='bobby' " +
					 "ORDER BY city ASC, state DESC, country;", select.getStatement());
	}
	
	public void testCreateWithColumnString() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		select.addClause("select", "column1, column2, column3");
		select.addClause("where", "Name='bobby'");
		select.addClause("order by", "city ASC");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM sakila.TestTable " +
					 "WHERE Name='bobby' " +
					 "ORDER BY city ASC;", select.getStatement());
	}
	
	public void testCreateWithDefaultAll() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT * FROM sakila.TestTable WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateWithExplicitTable() throws Exception {
		SQLStatement select = new SelectStatement("test.TestTable");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT * FROM test.TestTable WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateJoin() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		List<String> columnList = (List<String>) new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		select.addClause("select", columnList);
		select.addClause("join", "YourTable ON TestTable.column1=YourTable.column1");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM sakila.TestTable " +
					 "JOIN YourTable ON TestTable.column1=YourTable.column1 " +
					 "WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateMultiJoin() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		List<String> columnList = (List<String>) new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		select.addClause("select", columnList);
		select.addClause("join", "YourTable ON TestTable.column1=YourTable.column1");
		select.addClause("join", "NewTable ON YourTable.column1=NewTable.column1");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM sakila.TestTable " +
					 "JOIN YourTable ON TestTable.column1=YourTable.column1 " +
					 "JOIN NewTable ON YourTable.column1=NewTable.column1 " +
					 "WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateWithLeftJoin() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		List<String> columnList = (List<String>) new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		select.addClause("select", columnList);
		select.addClause("join", "YourTable ON TestTable.column1=YourTable.column1", "left");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM sakila.TestTable " +
					 "LEFT JOIN YourTable ON TestTable.column1=YourTable.column1 " +
					 "WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateWithRightJoin() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		List<String> columnList = (List<String>) new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		select.addClause("select", columnList);
		select.addClause("join", "YourTable ON TestTable.column1=YourTable.column1", "right");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM sakila.TestTable " +
					 "RIGHT JOIN YourTable ON TestTable.column1=YourTable.column1 " +
					 "WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateWithNestedQuery() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		SQLStatement nested = new SelectStatement("YourTable");
		
		nested.addClause("where", "Name='bobby'");
		
		List<String> columnList = (List<String>) new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		select.addClause("select", columnList);
		select.addClause("from", nested);
		select.addClause("where", "Age=29");
		
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM (" +
					 	"SELECT * " +
					 	"FROM sakila.YourTable " +
					 	"WHERE Name='bobby'" +
					 	") " +
				 	 "WHERE Age=29;", select.getStatement());
	}
	
	public void testCreateWithSeparateAddWheres() throws Exception {
		SQLStatement select = new SelectStatement("TestTable");
		select.addClause("where", "Name='bobby'");
		select.addClause("where", "Age=29");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT * " +
					 "FROM sakila.TestTable " +
					 "WHERE Name='bobby' " +
					   "AND Age=29;", select.getStatement());
	}
	
	public void testCreateWithJSONObject() throws Exception {
		String jsonString = "{'type' : 'SELECT', " +
				 "'select' : '*', " +
				 "'table' : 'table1'," +
				 "'join' : [" +
				           "{'table' : 'table2', 'lhs' : 'table1.attr', 'rhs' : 'table2.attr'}," +
				           "{'table' : 'table3', 'lhs' : 'table2.attr', 'rhs' : 'table3.attr'}," +
				           "{'table' : 'table4', 'lhs' : 'table3.attr', 'rhs' : 'table4.attr', 'mod' : 'left'}" +
				          "], " +
				 "'where' : [" +
				            "{'attribute' : 'attr1', 'value' : 'val1'}," +
				            "{'attribute' : 'attr2', 'value' : 'val2'}" +
				           "], " +
				 "'order by' : [" +
				 			"{'instruction' : 'attr1'}," +
				 			"{'instruction' : 'attr2 DESC'}" +
				 			  "]" +
				"}";
		JSONObject json = new JSONObject(jsonString);
		SQLStatement select = new SelectStatement(json);
		
		assertEquals("select", select.getType());
		assertEquals("table1", select.getTableName());
		assertEquals("SELECT * " +
					 "FROM sakila.table1 " +
					 "JOIN table2 ON table1.attr=table2.attr " +
					 "JOIN table3 ON table2.attr=table3.attr " +
					 "LEFT JOIN table4 ON table3.attr=table4.attr " +
					 "WHERE attr1='val1' " +
					   "AND attr2='val2' " +
					 "ORDER BY attr1, attr2 DESC;", select.getStatement());
	}
	
//	public void testExecute() throws Exception {
//		
//		String jsonString = "{\"type\":\"SELECT\",\"select\":\"photos.id, filename, datetaken, city, state, country, event, uploadedby, dateuploaded, firstname, lastname, tag\",\"table\":\"photostore.photos\",\"join\":[{\"table\":\"photostore.hasperson\",\"lhs\":\"photos.id\",\"rhs\":\"hasperson.id\"},{\"table\":\"photostore.hastag\",\"lhs\":\"photos.id\",\"rhs\":\"hastag.id\"}],\"where\":[],\"order by\":[{\"instruction\":\"dateuploaded DESC\"}]}:";
//		JSONObject json = new JSONObject(jsonString);
//		
//		SQLStatement select = new SelectStatement(json);
//		Result result = select.execute();
//		assertNotNull(result);
//	}

}
