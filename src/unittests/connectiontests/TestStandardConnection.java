package unittests.connectiontests;

import junit.framework.TestCase;
import unittests.testobjects.TestStatement;
import zoedb.SQLStatement;
import zoedb.connection.DBConnection;
import zoedb.connection.StandardConnection;
import zoedb.result.Result;

public class TestStandardConnection extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.SelectStatement");
			Class.forName("zoedb.InsertStatement");
			Class.forName("zoedb.UpdateStatement");
			Class.forName("zoedb.DeleteStatement");
			Class.forName("zoedb.SelectClause");
			Class.forName("zoedb.FromClause");
			Class.forName("zoedb.WhereClause");
			Class.forName("zoedb.SetClause");
			Class.forName("zoedb.InsertClause");
			Class.forName("zoedb.ValuesClause");
			Class.forName("zoedb.UpdateClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testExecuteStatement() throws Exception {
		DBConnection con = new StandardConnection();
		SQLStatement test = new TestStatement();
		Result result = con.execute(test);
		assertNotNull(result);
		assertEquals(0, result.getNumberOfColumns());
	}

}
