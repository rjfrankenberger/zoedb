package unittests;

import junit.framework.TestCase;

import zoedb.DeleteStatement;
import zoedb.InsertStatement;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.SelectStatement;
import zoedb.UpdateStatement;
import zoedb.exception.TypeNotRegisteredException;

public class TestSQLStatementFactory extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.SelectStatement");
			Class.forName("zoedb.InsertStatement");
			Class.forName("zoedb.UpdateStatement");
			Class.forName("zoedb.DeleteStatement");
			Class.forName("zoedb.FromClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetSelectStatement() throws Exception {
		setUp();
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		assertTrue(select instanceof SelectStatement);
		assertEquals("TestTable", select.getTableName());
	}

	public void testGetInsertStatement() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement insert = factory.getSQLStatement("insert", "TestTable");
		assertTrue(insert instanceof InsertStatement);
		assertEquals("TestTable", insert.getTableName());
	}
	
	public void testGetUpdateStatement() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement update = factory.getSQLStatement("update", "TestTable");
		assertTrue(update instanceof UpdateStatement);
		assertEquals("TestTable", update.getTableName());
	}
	
	public void testGetDeleteStatement() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement delete = factory.getSQLStatement("delete", "TestTable");
		assertTrue(delete instanceof DeleteStatement);
		assertEquals("TestTable", delete.getTableName());
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
		
		assertEquals(null, select.getTableName());
		assertEquals(null, insert.getTableName());
		assertEquals(null, update.getTableName());
		assertEquals(null, delete.getTableName());
	}

}
