package unittests.clausetests;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestDeleteClause extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.DeleteClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testCreateWithString() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause delete = factory.getClause("delete", "TestTable");
		assertEquals("delete", delete.getType());
		assertEquals("TestTable", delete.getBody());
		assertEquals("DELETE FROM TestTable", delete.getClause());
	}

}
