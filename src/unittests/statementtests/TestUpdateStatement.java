package unittests.statementtests;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import org.json.JSONObject;

import zoedb.SQLStatement;
import zoedb.UpdateStatement;
import zoedb.result.Result;

public class TestUpdateStatement extends TestCase {

	public void testCreateWithSetString() throws Exception {
		SQLStatement update = new UpdateStatement("TestTable");
		update.addClause("set", "Name='bobby', Age=29");
		update.addClause("where", "Name='robert'");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getTableName());
		assertEquals("UPDATE sakila.TestTable " +
					 "SET Name='bobby', Age=29 " +
					 "WHERE Name='robert';", update.getStatement());
	}
	
	public void testCreateWithExplicitTable() throws Exception {
		SQLStatement update = new UpdateStatement("test.TestTable");
		update.addClause("set", "Name='bobby', Age=29");
		update.addClause("where", "Name='robert'");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getTableName());
		assertEquals("UPDATE test.TestTable " +
					 "SET Name='bobby', Age=29 " +
					 "WHERE Name='robert';", update.getStatement());
	}
	
	public void testCreateWithSetList() throws Exception {
		SQLStatement update = new UpdateStatement("TestTable");
		ArrayList<String> list = new ArrayList<String>();
		list.add("Name='bobby'");
		list.add("Age=29");
		update.addClause("set", list);
		update.addClause("where", "Name='robert'");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getTableName());
		assertEquals("UPDATE sakila.TestTable " +
				 "SET Name='bobby', Age=29 " +
				 "WHERE Name='robert';", update.getStatement());
	}
	
	public void testCreateWithSetMap() throws Exception {
		SQLStatement update = new UpdateStatement("TestTable");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Name", "'bobby'");
		map.put("Age", "29");
		update.addClause("set", map);
		update.addClause("where", "Name='robert'");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getTableName());
		assertEquals("UPDATE sakila.TestTable " +
				 "SET Name='bobby', Age=29 " +
				 "WHERE Name='robert';", update.getStatement());
	}
	
	public void testCreateWithJSONObject() throws Exception {
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
		SQLStatement update = new UpdateStatement(json);
		
		assertEquals("update", update.getType());
		assertEquals("tableName", update.getTableName());
		assertEquals("UPDATE sakila.tableName " +
					 "SET attr1='val1', attr2='val2' " +
					 "WHERE attr3='val3' " +
					   "AND attr4='val4';", update.getStatement());
	}
	
	public void testExecute() throws Exception {
		SQLStatement update = new UpdateStatement("test.mytable");
		update.addClause("set", "firstname='steph'");
		update.addClause("where", "firstname='stephanie'");
		Result result = update.execute();
		assertNotNull(result);
		assertEquals(0, result.getNumberOfColumns());
	}

}
