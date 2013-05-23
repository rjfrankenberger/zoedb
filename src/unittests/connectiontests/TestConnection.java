package unittests.connectiontests;

import junit.framework.TestCase;

import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
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
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		Result result = con.executeStatement(select);
		assertNotNull(result);
	}

}
