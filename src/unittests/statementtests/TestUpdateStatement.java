package unittests.statementtests;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.result.Result;

public class TestUpdateStatement extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.UpdateStatement");
			Class.forName("zoedb.UpdateClause");
			Class.forName("zoedb.SetClause");
			Class.forName("zoedb.WhereClause");
			Class.forName("zoedb.connection.StandardConnection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testCreateWithSetString() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement update = factory.getSQLStatement("update", "TestTable");
		update.addClause("set", "Name='bobby', Age=29");
		update.addClause("where", "Name='robert'");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getTableName());
		assertEquals("UPDATE TestTable " +
					 "SET Name='bobby', Age=29 " +
					 "WHERE Name='robert';", update.getStatement());
	}
	
	public void testCreateWithSetList() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement update = factory.getSQLStatement("update", "TestTable");
		ArrayList<String> list = new ArrayList<String>();
		list.add("Name='bobby'");
		list.add("Age=29");
		update.addClause("set", list);
		update.addClause("where", "Name='robert'");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getTableName());
		assertEquals("UPDATE TestTable " +
				 "SET Name='bobby', Age=29 " +
				 "WHERE Name='robert';", update.getStatement());
	}
	
	public void testCreateWithSetMap() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement update = factory.getSQLStatement("update", "TestTable");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Name", "'bobby'");
		map.put("Age", "29");
		update.addClause("set", map);
		update.addClause("where", "Name='robert'");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getTableName());
		assertEquals("UPDATE TestTable " +
				 "SET Name='bobby', Age=29 " +
				 "WHERE Name='robert';", update.getStatement());
	}
	
	public void testExecute() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement update = factory.getSQLStatement("update", "test.mytable");
		update.addClause("set", "firstname='steph'");
		update.addClause("where", "firstname='stephanie'");
		Result result = update.execute();
		assertNull(result);
	}

}
