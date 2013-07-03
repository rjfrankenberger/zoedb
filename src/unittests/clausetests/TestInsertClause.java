package unittests.clausetests;

import java.util.ArrayList;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.InsertClause;

public class TestInsertClause extends TestCase {

	public void testCreateWithString() throws Exception {
		Clause insert = new InsertClause("TestTable", "column1, column2, column3");
		assertEquals("insert", insert.getType());
		assertEquals("sakila.TestTable (column1, column2, column3)", insert.getBody());
		assertEquals("INSERT INTO sakila.TestTable (column1, column2, column3)", insert.getClause());
	}
	
	public void testCreateWithExplicitTable() throws Exception {
		Clause insert = new InsertClause("test.TestTable", "column1, column2, column3");
		assertEquals("insert", insert.getType());
		assertEquals("test.TestTable (column1, column2, column3)", insert.getBody());
		assertEquals("INSERT INTO test.TestTable (column1, column2, column3)", insert.getClause());
	}
	
	public void testCreateWithList() throws Exception {
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("column1");
		columns.add("column2");
		columns.add("column3");
		Clause insert = new InsertClause("TestTable", columns);
		assertEquals("insert", insert.getType());
		assertEquals("sakila.TestTable (column1, column2, column3)", insert.getBody());
		assertEquals("INSERT INTO sakila.TestTable (column1, column2, column3)", insert.getClause());
	}

}
