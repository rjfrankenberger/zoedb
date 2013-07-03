package unittests.statementtests;

import junit.framework.TestCase;

import org.json.JSONObject;

import zoedb.DeleteStatement;
import zoedb.SQLStatement;
import zoedb.result.Result;

public class TestDeleteStatement extends TestCase {

	public void testCreateWithWhereString() throws Exception {
		SQLStatement delete = new DeleteStatement("TestTable");
		delete.addClause("where", "Name='bobby'");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getTableName());
		assertEquals("DELETE FROM sakila.TestTable " +
					 "WHERE Name='bobby';", delete.getStatement());
	}
	
	public void testCreateWithMultiWhereString() throws Exception {
		SQLStatement delete = new DeleteStatement("TestTable");
		delete.addClause("where", "Name='bobby'");
		delete.addClause("where", "Age=29");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getTableName());
		assertEquals("DELETE FROM sakila.TestTable " +
					 "WHERE Name='bobby' " +
					 "AND Age=29;", delete.getStatement());
	}
	
	public void testCreateWithExplicitTable() throws Exception {
		SQLStatement delete = new DeleteStatement("test.TestTable");
		delete.addClause("where", "Name='bobby'");
		delete.addClause("where", "Age=29");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getTableName());
		assertEquals("DELETE FROM test.TestTable " +
					 "WHERE Name='bobby' " +
					 "AND Age=29;", delete.getStatement());
	}
	
	public void testCreateWithJSONObject() throws Exception {
		String jsonString = "{'type' : 'DELETE', " +
				 "'table' : 'table1'," +
				 "'where' : [" +
				            "{'attribute' : 'attr1', 'value' : 'val1'}," +
				            "{'attribute' : 'attr2', 'value' : 'val2'}" +
				           "]" +
				"}";
		JSONObject json = new JSONObject(jsonString);
		SQLStatement delete = new DeleteStatement(json);
		
		assertEquals("delete", delete.getType());
		assertEquals("table1", delete.getTableName());
		assertEquals("DELETE FROM sakila.table1 " +
					 "WHERE attr1='val1' " +
					   "AND attr2='val2';", delete.getStatement());
	}

	public void testExecute() throws Exception {
		SQLStatement delete = new DeleteStatement("language");
		delete.addClause("where", "name='Spanish'");
		Result result = delete.execute();
		assertNotNull(result);
		assertEquals(0, result.getNumberOfColumns());
	}
}
