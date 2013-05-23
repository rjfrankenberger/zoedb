package unittests.statementtests;

import junit.framework.TestCase;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.result.Result;

public class TestDeleteStatement extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.DeleteStatement");
			Class.forName("zoedb.DeleteClause");
			Class.forName("zoedb.WhereClause");
			Class.forName("zoedb.connection.StandardConnection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testCreateWithWhereString() throws Exception {
		setUp();
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement delete = factory.getSQLStatement("delete", "TestTable");
		delete.addClause("where", "Name='bobby'");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getTableName());
		assertEquals("DELETE FROM TestTable " +
					 "WHERE Name='bobby';", delete.getStatement());
	}
	
	public void testCreateWithMultiWhereString() throws Exception {
		setUp();
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement delete = factory.getSQLStatement("delete", "TestTable");
		delete.addClause("where", "Name='bobby'");
		delete.addClause("where", "Age=29");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getTableName());
		assertEquals("DELETE FROM TestTable " +
					 "WHERE Name='bobby' " +
					 "AND Age=29;", delete.getStatement());
	}

	public void testExecute() throws Exception {
		setUp();
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement delete = factory.getSQLStatement("delete", "test.mytable");
		delete.addClause("where", "firstname='debra'");
		Result result = delete.execute();
		assertNull(result);
	}
}
