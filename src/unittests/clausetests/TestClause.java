package unittests.clausetests;

import zoedb.Clause;
import junit.framework.TestCase;

public class TestClause extends TestCase {
	
	public void testNull() throws Exception {
		assertEquals("", Clause.NULL.getType());
		assertEquals("", Clause.NULL.getBody());
		assertEquals("", Clause.NULL.getClause());
	}

}
