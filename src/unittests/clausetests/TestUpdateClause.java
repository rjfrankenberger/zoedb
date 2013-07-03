package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.UpdateClause;

public class TestUpdateClause extends TestCase {

	public void testCreateWithString() throws Exception {
		Clause update = new UpdateClause("TestTable");
		assertEquals("update", update.getType());
		assertEquals("sakila.TestTable", update.getBody());
		assertEquals("UPDATE sakila.TestTable", update.getClause());
	}
	
	public void testCreateWithExplicitTable() throws Exception {
		Clause update = new UpdateClause("test.TestTable");
		assertEquals("update", update.getType());
		assertEquals("test.TestTable", update.getBody());
		assertEquals("UPDATE test.TestTable", update.getClause());
	}

}
