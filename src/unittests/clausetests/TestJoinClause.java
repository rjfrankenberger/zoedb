package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.JoinClause;

public class TestJoinClause extends TestCase {

	public void testCreateWithString() throws Exception {
		Clause join = new JoinClause ("YourTable ON Age=29");
		assertEquals("join", join.getType());
		assertEquals("sakila.YourTable ON Age=29", join.getBody());
		assertEquals("JOIN sakila.YourTable ON Age=29", join.getClause());
	}
	
	public void testLeftJoin() throws Exception {
		Clause join = new JoinClause("YourTable ON Age=29", "left");
		assertEquals("join", join.getType());
		assertEquals("sakila.YourTable ON Age=29", join.getBody());
		assertEquals("LEFT JOIN sakila.YourTable ON Age=29", join.getClause());
	}
	
	public void testRightJoin() throws Exception {
		Clause join = new JoinClause("YourTable ON Age=29", "right");
		assertEquals("join", join.getType());
		assertEquals("sakila.YourTable ON Age=29", join.getBody());
		assertEquals("RIGHT JOIN sakila.YourTable ON Age=29", join.getClause());
	}

}
