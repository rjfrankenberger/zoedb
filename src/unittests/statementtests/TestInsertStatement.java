package unittests.statementtests;

import java.util.ArrayList;

import junit.framework.TestCase;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.result.Result;

public class TestInsertStatement extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.InsertStatement");
			Class.forName("zoedb.InsertClause");
			Class.forName("zoedb.ValuesClause");
			Class.forName("zoedb.connection.StandardConnection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testCreateWithColumnString() throws Exception {
		setUp();
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement insert = factory.getSQLStatement("insert", "TestTable");
		insert.addClause("insert", "column1, column2, column3");
		insert.addClause("values", "value1, value2, value3");
		assertEquals("insert", insert.getType());
		assertEquals("TestTable", insert.getTableName());
		assertEquals("INSERT INTO TestTable (column1, column2, column3) " +
					 "VALUES(value1, value2, value3);", insert.getStatement());
	}
	
	public void testCreateWithColumnList() throws Exception {
		setUp();
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement insert = factory.getSQLStatement("insert", "TestTable");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("column1");
		columns.add("column2");
		columns.add("column3");
		insert.addClause("insert", columns);
		insert.addClause("values", "value1, value2, value3");
		assertEquals("insert", insert.getType());
		assertEquals("TestTable", insert.getTableName());
		assertEquals("INSERT INTO TestTable (column1, column2, column3) " +
					 "VALUES(value1, value2, value3);", insert.getStatement());
	}
	
	public void testExecute() throws Exception {
		setUp();
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement insert = factory.getSQLStatement("insert", "test.mytable");
		insert.addClause("insert", "firstname, lastname");
		insert.addClause("values", "'zoe', 'frankenberger'");
		Result result = insert.execute();
		assertNull(result);
	}

}