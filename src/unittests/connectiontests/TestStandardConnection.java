package unittests.connectiontests;

import junit.framework.TestCase;
import unittests.testobjects.TestStatement;
import zoedb.SQLStatement;
import zoedb.connection.DBConnection;
import zoedb.connection.StandardConnection;
import zoedb.result.Result;

public class TestStandardConnection extends TestCase {
	
	public void testStatement() throws Exception {
		DBConnection con = new StandardConnection();
		assertEquals("standard", con.getType());
	}

	public void testExecuteStatement() throws Exception {
		DBConnection con = new StandardConnection();
		SQLStatement test = new TestStatement();
		Result result = con.execute(test);
		assertNotNull(result);
		assertEquals(0, result.getNumberOfColumns());
	}

}
