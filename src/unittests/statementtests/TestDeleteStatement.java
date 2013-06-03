package unittests.statementtests;

import junit.framework.TestCase;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.result.Result;

public class TestDeleteStatement extends TestCase {

	public void testCreateWithWhereString() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement delete = factory.getSQLStatement("delete", "TestTable");
		delete.addClause("where", "Name='bobby'");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getTableName());
		assertEquals("DELETE FROM TestTable " +
					 "WHERE Name='bobby';", delete.getStatement());
	}
	
	public void testCreateWithMultiWhereString() throws Exception {
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
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement delete = factory.getSQLStatement("delete", "test.mytable");
		delete.addClause("where", "firstname='debra'");
		Result result = delete.execute();
		assertNotNull(result);
		assertEquals(0, result.getNumberOfColumns());
	}
}
