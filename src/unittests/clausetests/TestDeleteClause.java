package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.DeleteClause;

public class TestDeleteClause extends TestCase {

	public void testCreateWithString() throws Exception {
		Clause delete = new DeleteClause("TestTable");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getBody());
		assertEquals("DELETE FROM sakila.TestTable", delete.getClause());
	}
	
	public void testSpecifyConnectionName() throws Exception {
		Clause delete = new DeleteClause("TestTable", "secondary");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getBody());
		assertEquals("DELETE FROM sakila.TestTable", delete.getClause());
	}

}
