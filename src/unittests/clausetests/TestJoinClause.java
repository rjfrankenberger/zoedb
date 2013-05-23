package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestJoinClause extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.JoinClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testCreateWithString() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause join = factory.getClause("join", "YourTable ON Age=29");
		assertEquals("join", join.getType());
		assertEquals("YourTable ON Age=29", join.getBody());
		assertEquals("JOIN YourTable ON Age=29", join.getClause());
	}

}
