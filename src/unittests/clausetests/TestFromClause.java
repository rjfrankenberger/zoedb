package unittests.clausetests;

import junit.framework.TestCase;
import unittests.testobjects.TestStatement;
import zoedb.Clause;
import zoedb.ClauseFactory;
import zoedb.SQLStatement;

public class TestFromClause extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.SelectStatement");
			Class.forName("zoedb.SelectClause");
			Class.forName("zoedb.FromClause");
			Class.forName("zoedb.WhereClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testCreateWithString() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause from = factory.getClause("from", "TestTable");
		assertEquals("from", from.getType());
		assertEquals("TestTable", from.getBody());
		assertEquals("FROM TestTable", from.getClause());
	}
	
	public void testCreateWithNestedStatement() throws Exception {
		setUp();
		ClauseFactory clauseFactory = ClauseFactory.getInstance();
		SQLStatement nested = new TestStatement();
		nested.addClause("where", "Name='bobby'");
		Clause from = clauseFactory.getClause("from", nested);
		
		assertEquals("from", from.getType());
		assertEquals("(TEST STATEMENT)", from.getBody());
		assertEquals("FROM (TEST STATEMENT)", from.getClause());
	}

}
