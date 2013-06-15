package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestDeleteClause extends TestCase {

	public void testCreateWithString() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause delete = factory.getClause("delete", "TestTable");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getBody());
		assertEquals("DELETE FROM test.TestTable", delete.getClause());
	}
	
	public void testSpecifyConnectionName() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause delete = factory.getClause("delete", "TestTable", "secondary");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getBody());
		assertEquals("DELETE FROM test.TestTable", delete.getClause());
	}

}
