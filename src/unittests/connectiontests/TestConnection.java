package unittests.connectiontests;

import junit.framework.TestCase;
import unittests.testobjects.TestStatement;
import zoedb.SQLStatement;
import zoedb.connection.Connection;
import zoedb.result.Result;

public class TestConnection extends TestCase {

	public void testExecuteSQL() throws Exception {
		zoedb.util.TypeLoader.loadTypes();
		Connection con = new Connection();
		SQLStatement test = new TestStatement();
		Result result = con.executeStatement(test);
		assertNotNull(result);
		assertEquals(0, result.getNumberOfColumns());
	}

}
