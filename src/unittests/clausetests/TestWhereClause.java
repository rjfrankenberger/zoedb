package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestWhereClause extends TestCase {

	public void testCreateWithString() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause where = factory.getClause("where", "Name='bobby'");
		assertEquals("where", where.getType());
		assertEquals("Name='bobby'", where.getBody());
		assertEquals("WHERE Name='bobby'", where.getClause());
	}

}
