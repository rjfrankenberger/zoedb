package unittests.clausetests;

import junit.framework.TestCase;
import unittests.testobjects.TestStatement;
import zoedb.Clause;
import zoedb.ClauseFactory;
import zoedb.SQLStatement;

public class TestFromClause extends TestCase {
	
	public void testCreateWithString() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause from = factory.getClause("from", "test.TestTable");
		assertEquals("from", from.getType());
		assertEquals("test.TestTable", from.getBody());
		assertEquals("FROM test.TestTable", from.getClause());
	}
	
	public void testCreateWithNestedStatement() throws Exception {
		ClauseFactory clauseFactory = ClauseFactory.getInstance();
		SQLStatement nested = new TestStatement();
		nested.addClause("where", "Name='bobby'");
		Clause from = clauseFactory.getClause("from", nested);
		
		assertEquals("from", from.getType());
		assertEquals("(TEST STATEMENT)", from.getBody());
		assertEquals("FROM (TEST STATEMENT)", from.getClause());
	}
	
	public void testDefaultSchema() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause from = factory.getClause("from", "TestTable");
		assertEquals("from", from.getType());
		assertEquals("test.TestTable", from.getBody());
		assertEquals("FROM test.TestTable", from.getClause());
	}

}
