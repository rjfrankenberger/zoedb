package unittests.connectiontests;

import junit.framework.TestCase;
import unittests.testobjects.TestStatement;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
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
	
	public void testActualStatement() throws Exception {
		StandardConnection con = new StandardConnection();
		
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "language");
		Result result = con.execute(select);
		String testString = "[" +
								"{\"language_id\" : 1, \"name\" : \"English\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 2, \"name\" : \"Italian\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 3, \"name\" : \"Japanese\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 4, \"name\" : \"Mandarin\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 5, \"name\" : \"French\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 6, \"name\" : \"German\", \"last_update\" : \"2006-02-15 05:02:19\"}" +
							"]";
		assertEquals(testString, result.JSONString());
	}

}
