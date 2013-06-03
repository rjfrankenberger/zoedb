package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestUpdateClause extends TestCase {

	public void testCreateWithString() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause update = factory.getClause("update", "TestTable");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getBody());
		assertEquals("UPDATE TestTable", update.getClause());
	}

}
