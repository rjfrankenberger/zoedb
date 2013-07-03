package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.WhereClause;

public class TestWhereClause extends TestCase {

	public void testCreateWithString() throws Exception {
		Clause where = new WhereClause("Name='bobby'");
		assertEquals("where", where.getType());
		assertEquals("Name='bobby'", where.getBody());
		assertEquals("WHERE Name='bobby'", where.getClause());
	}

}
