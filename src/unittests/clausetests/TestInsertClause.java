package unittests.clausetests;

import java.util.ArrayList;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestInsertClause extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.InsertClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testCreateWithString() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause insert = factory.getClause("insert", "TestTable", "column1, column2, column3");
		assertEquals("insert", insert.getType());
		assertEquals("TestTable (column1, column2, column3)", insert.getBody());
		assertEquals("INSERT INTO TestTable (column1, column2, column3)", insert.getClause());
	}
	
	public void testCreateWithList() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("column1");
		columns.add("column2");
		columns.add("column3");
		Clause insert = factory.getClause("insert", "TestTable", columns);
		assertEquals("insert", insert.getType());
		assertEquals("TestTable (column1, column2, column3)", insert.getBody());
		assertEquals("INSERT INTO TestTable (column1, column2, column3)", insert.getClause());
	}

}
