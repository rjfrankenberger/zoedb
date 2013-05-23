package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestUpdateClause extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.UpdateClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testCreateWithString() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause update = factory.getClause("update", "TestTable");
		assertEquals("update", update.getType());
		assertEquals("TestTable", update.getBody());
		assertEquals("UPDATE TestTable", update.getClause());
	}

}
