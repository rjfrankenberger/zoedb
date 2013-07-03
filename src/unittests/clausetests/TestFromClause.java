package unittests.clausetests;

import junit.framework.TestCase;
import unittests.testobjects.TestStatement;
import zoedb.Clause;
import zoedb.FromClause;
import zoedb.SQLStatement;

public class TestFromClause extends TestCase {
	
	public void testCreateWithString() throws Exception {
		Clause from = new FromClause("test.TestTable");
		assertEquals("from", from.getType());
		assertEquals("test.TestTable", from.getBody());
		assertEquals("FROM test.TestTable", from.getClause());
	}
	
	public void testCreateWithNestedStatement() throws Exception {
		SQLStatement nested = new TestStatement();
		nested.addClause("where", "Name='bobby'");
		Clause from = new FromClause(nested);
		
		assertEquals("from", from.getType());
		assertEquals("(TEST STATEMENT)", from.getBody());
		assertEquals("FROM (TEST STATEMENT)", from.getClause());
	}
	
	public void testDefaultSchema() throws Exception {
		Clause from = new FromClause("TestTable");
		assertEquals("from", from.getType());
		assertEquals("sakila.TestTable", from.getBody());
		assertEquals("FROM sakila.TestTable", from.getClause());
	}

}
