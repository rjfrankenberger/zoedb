package unittests.clausetests;

import java.util.ArrayList;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestSelectClause extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.SelectClause");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void testCreateWithString() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause select = factory.getClause("select", "column1");
		assertEquals("select", select.getType());
		assertEquals("column1", select.getBody());
		assertEquals("SELECT column1", select.getClause());
	}
	
	public void testCreateWithList() throws Exception {
		setUp();
		ClauseFactory factory = ClauseFactory.getInstance();
		ArrayList<String> columnList = new ArrayList<String>();
		columnList.add("column1");
		columnList.add("column2");
		columnList.add("column3");
		Clause select = factory.getClause("select", columnList);
		assertEquals("select", select.getType());
		assertEquals("column1, column2, column3", select.getBody());
		assertEquals("SELECT column1, column2, column3", select.getClause());
	}
}
