package unittests.connectiontests;

import junit.framework.TestCase;
import unittests.testobjects.TestStatement;
import zoedb.SQLStatement;
import zoedb.connection.Connection;
import zoedb.result.Result;

public class TestConnection extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.connection.StandardConnection");
			Class.forName("zoedb.SelectStatement");
			Class.forName("zoedb.SelectClause");
			Class.forName("zoedb.FromClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testExecuteSQL() throws Exception {
		setUp();
		Connection con = new Connection();
		SQLStatement test = new TestStatement();
		Result result = con.executeStatement(test);
		assertNotNull(result);
		assertEquals(0, result.getNumberOfColumns());
	}

}
