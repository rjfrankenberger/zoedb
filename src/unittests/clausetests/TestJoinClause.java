package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestJoinClause extends TestCase {

	public void testCreateWithString() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause join = factory.getClause("join", "YourTable ON Age=29");
		assertEquals("join", join.getType());
		assertEquals("YourTable ON Age=29", join.getBody());
		assertEquals("JOIN YourTable ON Age=29", join.getClause());
	}

}
