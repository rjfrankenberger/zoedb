package unittests.statementtests;

import java.util.ArrayList;
import java.util.List;

import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.result.Result;
import junit.framework.TestCase;

public class TestSelectStatement extends TestCase {

	public void testCreateWithColumnList() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		ArrayList<String> columnList = new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		select.addClause("select", (List) columnList);
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 FROM TestTable WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateWithColumnString() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		select.addClause("select", "column1, column2, column3");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 FROM TestTable WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateWithDefaultAll() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT * FROM TestTable WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateJoin() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		List<String> columnList = (List<String>) new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		select.addClause("select", columnList);
		select.addClause("join", "YourTable ON TestTable.column1=YourTable.column1");
		select.addClause("where", "Name='bobby'");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM TestTable " +
					 "JOIN YourTable ON TestTable.column1=YourTable.column1 " +
					 "WHERE Name='bobby';", select.getStatement());
	}
	
	public void testCreateWithNestedQuery() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		SQLStatement nested = factory.getSQLStatement("select", "YourTable");
		
		nested.addClause("where", "Name='bobby'");
		
		List<String> columnList = (List<String>) new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		select.addClause("select", columnList);
		select.addClause("from", nested);
		select.addClause("where", "Age=29");
		
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT column1, column2, column3 " +
					 "FROM (" +
					 	"SELECT * " +
					 	"FROM YourTable " +
					 	"WHERE Name='bobby'" +
					 	") " +
				 	 "WHERE Age=29;", select.getStatement());
	}
	
	public void testCreateWithSeparateAddWheres() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "TestTable");
		select.addClause("where", "Name='bobby'");
		select.addClause("where", "Age=29");
		assertEquals("select", select.getType());
		assertEquals("TestTable", select.getTableName());
		assertEquals("SELECT * " +
					 "FROM TestTable " +
					 "WHERE Name='bobby' " +
					   "AND Age=29;", select.getStatement());
	}
	
	public void testExecute() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "test.mytable");
		Result result = select.execute();
		assertNotNull(result);
	}

}
