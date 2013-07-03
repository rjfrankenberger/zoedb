package unittests.clausetests;

import java.util.ArrayList;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.SelectClause;

public class TestSelectClause extends TestCase {

	public void testCreateWithString() throws Exception {
		Clause select = new SelectClause("column1");
		assertEquals("select", select.getType());
		assertEquals("column1", select.getBody());
		assertEquals("SELECT column1", select.getClause());
	}
	
	public void testCreateWithList() throws Exception {
		ArrayList<String> columnList = new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		Clause select = new SelectClause(columnList);
		assertEquals("select", select.getType());
		assertEquals("column1, column2, column3", select.getBody());
		assertEquals("SELECT column1, column2, column3", select.getClause());
	}
}
