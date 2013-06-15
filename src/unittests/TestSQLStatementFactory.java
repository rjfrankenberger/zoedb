package unittests;

import org.json.JSONObject;

import junit.framework.TestCase;

import zoedb.DeleteStatement;
import zoedb.InsertStatement;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.SelectStatement;
import zoedb.UpdateStatement;
import zoedb.exception.TypeNotRegisteredException;

public class TestSQLStatementFactory extends TestCase {
	
	public void testGetSelectStatement() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		assertTrue(select instanceof SelectStatement);
		assertEquals("TestTable", select.getTableName());
	}
	
	public void testGetSelectWithJSON() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		String jsonString = "{'type' : 'SELECT', " +
				 "'select' : '*', " +
				 "'table' : 'table1'," +
				 "'join' : [" +
				           "{'table' : 'table2', 'lhs' : 'table1.attr', 'rhs' : 'table2.attr'}," +
				           "{'table' : 'table3', 'lhs' : 'table2.attr', 'rhs' : 'table3.attr'}" +
				          "], " +
				 "'where' : [" +
				            "{'attribute' : 'attr1', 'value' : 'val1'}," +
				            "{'attribute' : 'attr2', 'value' : 'val2'}" +
				           "]" +
				"}";
		JSONObject json = new JSONObject(jsonString);
		SQLStatement select = factory.getSQLStatement("select", json);
		assertTrue(select instanceof SelectStatement);
		assertEquals("table1", select.getTableName());
		assertEquals("select", select.getType());
	}

	public void testGetInsertStatement() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement insert = factory.getSQLStatement("insert", "TestTable");
		assertTrue(insert instanceof InsertStatement);
		assertEquals("TestTable", insert.getTableName());
	}
	
	public void testGetInsertWithJSON() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		String jsonString = "{'type' : 'INSERT', " +
				 "'table' : 'tableName'," +
				 "'insert' : [" +
				            "{'attribute' : 'attr1', 'value' : 'val1'}," +
				            "{'attribute' : 'attr2', 'value' : 'val2'}" +
				           "]" +
				"}";
		JSONObject json = new JSONObject(jsonString);
		SQLStatement insert = factory.getSQLStatement("insert", json);
		assertTrue(insert instanceof InsertStatement);
		assertEquals("tableName", insert.getTableName());
		assertEquals("insert", insert.getType());
	}
	
	public void testGetUpdateStatement() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement update = factory.getSQLStatement("update", "TestTable");
		assertTrue(update instanceof UpdateStatement);
		assertEquals("TestTable", update.getTableName());
	}
	
	public void testGetUpdateWithJSON() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		String jsonString = "{'type' : 'UPDATE', " +
				 "'table' : 'tableName'," +
				 "'set' : [" +
				            "{'attribute' : 'attr1', 'value' : 'val1'}," +
				            "{'attribute' : 'attr2', 'value' : 'val2'}" +
				           "]," +
		           "'where' : [" +
				            "{'attribute' : 'attr3', 'value' : 'val3'}," +
				            "{'attribute' : 'attr4', 'value' : 'val4'}" +
		           "]" +
				"}";
		JSONObject json = new JSONObject(jsonString);
		SQLStatement update = factory.getSQLStatement("update", json);
		assertTrue(update instanceof UpdateStatement);
		assertEquals("tableName", update.getTableName());
		assertEquals("update", update.getType());
	}
	
	public void testGetDeleteStatement() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement delete = factory.getSQLStatement("delete", "TestTable");
		assertTrue(delete instanceof DeleteStatement);
		assertEquals("TestTable", delete.getTableName());
	}
	
	public void testGetDeleteWithJSON() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		String jsonString = "{'type' : 'DELETE', " +
				 "'table' : 'tableName'," +
				 "'where' : [" +
				            "{'attribute' : 'attr1', 'value' : 'val1'}," +
				            "{'attribute' : 'attr2', 'value' : 'val2'}" +
				           "]" +
				"}";
		JSONObject json = new JSONObject(jsonString);
		SQLStatement delete = factory.getSQLStatement("delete", json);
		assertTrue(delete instanceof DeleteStatement);
		assertEquals("tableName", delete.getTableName());
		assertEquals("delete", delete.getType());
	}
	
	public void testGetInstancce() throws Exception {
		SQLStatementFactory factory1 = SQLStatementFactory.getInstance();
		SQLStatementFactory factory2 = SQLStatementFactory.getInstance();
		assertTrue(factory1.equals(factory2));
	}
	
	@SuppressWarnings("unused")
	public void testUnregisteredStatement() {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		try {
			SQLStatement stmt = factory.getSQLStatement("badType", "TestTable");
		} catch (Exception e) {
			assertTrue(e instanceof TypeNotRegisteredException);
		}
	}
	
	public void testGetSQLStatementNoTable() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select");
		SQLStatement insert = factory.getSQLStatement("insert");
		SQLStatement update = factory.getSQLStatement("update");
		SQLStatement delete = factory.getSQLStatement("delete");
		
		assertEquals("", select.getTableName());
		assertEquals("", insert.getTableName());
		assertEquals("", update.getTableName());
		assertEquals("", delete.getTableName());
	}

}
