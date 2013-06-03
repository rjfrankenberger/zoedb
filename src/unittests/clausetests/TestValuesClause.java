package unittests.clausetests;

import java.util.ArrayList;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestValuesClause extends TestCase {
	
	public void testCreateWithString() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause values = factory.getClause("values", "value1, value2, value3");
		assertEquals("values", values.getType());
		assertEquals("(value1, value2, value3)", values.getBody());
		assertEquals("VALUES(value1, value2, value3)", values.getClause());
	}
	
	public void testCreateWithList() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		ArrayList<String> valueList = new ArrayList<String>();
		valueList.add("value1");
		valueList.add("value2");
		valueList.add("value3");
		Clause values = factory.getClause("values", valueList);
		assertEquals("values", values.getType());
		assertEquals("(value1, value2, value3)", values.getBody());
		assertEquals("VALUES(value1, value2, value3)", values.getClause());
	}

}
