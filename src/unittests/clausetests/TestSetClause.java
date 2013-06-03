package unittests.clausetests;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.ClauseFactory;

public class TestSetClause extends TestCase {

	public void testCreateWithString() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause set = factory.getClause("set", "Name='bobby', Age=29");
		assertEquals("set", set.getType());
		assertEquals("Name='bobby', Age=29", set.getBody());
		assertEquals("SET Name='bobby', Age=29", set.getClause());
	}
	
	public void testCreateWithList() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		ArrayList<String> list = new ArrayList<String>();
		list.add("Name='bobby'");
		list.add("Age=29");
		Clause set = factory.getClause("set", list);
		assertEquals("set", set.getType());
		assertEquals("Name='bobby', Age=29", set.getBody());
		assertEquals("SET Name='bobby', Age=29", set.getClause());
	}
	
	public void testCreateWithMap() throws Exception {
		ClauseFactory factory = ClauseFactory.getInstance();
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Name", "'bobby'");
		map.put("Age", "29");
		Clause set = factory.getClause("set", map);
		assertEquals("set", set.getType());
		assertEquals("Name='bobby', Age=29", set.getBody());
		assertEquals("SET Name='bobby', Age=29", set.getClause());
	}

}
