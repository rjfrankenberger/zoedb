package unittests.clausetests;

import java.util.ArrayList;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;
import zoedb.ValuesClause;

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
	
	public void testCreateMultipleValuesClause() throws Exception {
		Clause values1 = new ValuesClause("value1, value2, value3");
		Clause values2 = new ValuesClause("value4, value5, value6");
		ArrayList<Clause> valuesList = new ArrayList<Clause>();
		valuesList.add(values1);
		valuesList.add(values2);
		Clause values = new ValuesClause(valuesList);
		assertEquals("values", values.getType());
		assertEquals("(value1, value2, value3),(value4, value5, value6)", values.getBody());
		assertEquals("VALUES(value1, value2, value3),(value4, value5, value6)", values.getClause());
	}

}
