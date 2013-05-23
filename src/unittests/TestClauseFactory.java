package unittests;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.exception.TypeNotRegisteredException;

public class TestClauseFactory extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.SelectStatement");
			Class.forName("zoedb.SelectClause");
			Class.forName("zoedb.FromClause");
			Class.forName("zoedb.WhereClause");
			Class.forName("zoedb.InsertClause");
			Class.forName("zoedb.SetClause");
			Class.forName("zoedb.ValuesClause");
			Class.forName("zoedb.UpdateClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetInstance() {
		ClauseFactory factory1 = ClauseFactory.getInstance();
		ClauseFactory factory2 = ClauseFactory.getInstance();
		assertTrue(factory1.equals(factory2));
	}
	
	public void testUnregisteredClauseType() {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		try {
			Clause clause = factory.getClause("badType", "dummyString");
		} catch (Exception e) {
			assertTrue(e instanceof TypeNotRegisteredException);
		}
	}
	
	public void testGetSelectClause() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause select = factory.getClause("select", "*");
		assertTrue(select instanceof Clause);
		assertEquals("select", select.getType());
		assertEquals("*", select.getBody());
	}
	
	public void testGetFromClause() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause from = factory.getClause("from", "TestTable");
		assertTrue(from instanceof Clause);
		assertEquals("from", from.getType());
		assertEquals("TestTable", from.getBody());
	}
	
	public void testGetWhereClause() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause where = factory.getClause("where", "Name='bobby'");
		assertTrue(where instanceof Clause);
		assertEquals("where", where.getType());
		assertEquals("Name='bobby'", where.getBody());
	}
	
	public void testGetInsertClause() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause insert = factory.getClause("insert", "TestTable", "column1, column2, column3");
		assertTrue(insert instanceof Clause);
		assertEquals("insert", insert.getType());
		assertEquals("TestTable (column1, column2, column3)", insert.getBody());
	}
	
	public void testGetClauseStringStringList() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("column1");
		columns.add("column2");
		columns.add("column3");
		Clause insert = factory.getClause("insert", "TestTable", columns);
		assertTrue(insert instanceof Clause);
		assertEquals("insert", insert.getType());
		assertEquals("TestTable (column1, column2, column3)", insert.getBody());
	}
	
	public void testGetClauseStringList() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		ArrayList<String> valueList = new ArrayList<String>();
		valueList.add("value1");
		valueList.add("value2");
		valueList.add("value3");
		Clause values = factory.getClause("values", valueList);
		assertTrue(values instanceof Clause);
		assertEquals("values", values.getType());
		assertEquals("(value1, value2, value3)", values.getBody());
	}
	
	public void testGetClauseStringMap() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Name", "'bobby'");
		map.put("Age", "29");
		map.put("Married", "'yes'");
		Clause set = factory.getClause("set", map);
		assertTrue(set instanceof Clause);
		assertEquals("set", set.getType());
		assertEquals("Name='bobby', Age=29, Married='yes'", set.getBody());
	}
	
	public void testGetClauseStringSQLStatement() throws Exception {
		setUp();
		ClauseFactory clauseFactory = ClauseFactory.getInstance();
		SQLStatementFactory sqlFactory = SQLStatementFactory.getInstance();
		SQLStatement nested = sqlFactory.getSQLStatement("select", "OtherTable");
		nested.addClause("where", "Name='bobby'");
		Clause from = clauseFactory.getClause("from", nested);
		assertTrue(from instanceof Clause);
		assertEquals("from", from.getType());
		assertEquals("(SELECT * FROM OtherTable WHERE Name='bobby')", from.getBody());
	}

}
