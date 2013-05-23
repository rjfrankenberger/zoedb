package unittests.connectiontests;

import junit.framework.TestCase;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.connection.DBConnection;
import zoedb.connection.StandardConnection;
import zoedb.result.Result;
import zoedb.result.ResultRow;

public class TestStandardConnection extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.SelectStatement");
			Class.forName("zoedb.InsertStatement");
			Class.forName("zoedb.UpdateStatement");
			Class.forName("zoedb.DeleteStatement");
//			Class.forName("zoedb.NullStatement");
			Class.forName("zoedb.SelectClause");
			Class.forName("zoedb.FromClause");
			Class.forName("zoedb.WhereClause");
			Class.forName("zoedb.SetClause");
			Class.forName("zoedb.InsertClause");
			Class.forName("zoedb.ValuesClause");
			Class.forName("zoedb.UpdateClause");
//			Class.forName("zoedb.NullClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testExecuteStatement() throws Exception {
		DBConnection con = new StandardConnection();
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "world.city");
		select.addClause("where", "CountryCode='BWA'");
		Result result = con.execute(select);
		
		System.out.println("size = " + result.getNumberOfColumns());
		for (ResultRow row : result) {
			System.out.println(row.get("ID").getValue());
		}
		
		assertNotNull(result);
	}
	
//	public void testExecuteInsertStatement() throws Exception {
//		DBConnection con = new StandardConnection();
//		SQLStatementFactory factory = SQLStatementFactory.getInstance();
//		SQLStatement insert = factory.getSQLStatement("insert", "test.mytable");
//		insert.addClause("insert", "firstname, lastname");
//		insert.addClause("values", "'bobby', 'frankenberger'");
//		Result result = con.execute(insert);
//		
//		assertNull(result);
//	}
	
//	public void testExecuteUpdateStatement() throws Exception {
//		DBConnection con = new StandardConnection();
//		SQLStatementFactory factory = SQLStatementFactory.getInstance();
//		SQLStatement update = factory.getSQLStatement("update", "test.mytable");
//		update.addClause("set", "firstname='robert'");
//		update.addClause("where", "firstname='bobby'");
//		Result result = con.execute(update);
//	}

//	public void testExecuteDeleteStatement() throws Exception {
//		DBConnection con = new StandardConnection();
//		SQLStatementFactory factory = SQLStatementFactory.getInstance();
//		SQLStatement delete = factory.getSQLStatement("delete", "test.mytable");
//		delete.addClause("where", "lastname='frankenberger'");
//		Result result = con.execute(delete);
//	}
}
