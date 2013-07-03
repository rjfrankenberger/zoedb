package unittests.clausetests;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.SetClause;

public class TestSetClause extends TestCase {

	public void testCreateWithString() throws Exception {
		Clause set = new SetClause("Name='bobby', Age=29");
		assertEquals("set", set.getType());
		assertEquals("Name='bobby', Age=29", set.getBody());
		assertEquals("SET Name='bobby', Age=29", set.getClause());
	}
	
	public void testCreateWithList() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Name='bobby'");
		list.add("Age=29");
		Clause set = new SetClause(list);
		assertEquals("set", set.getType());
		assertEquals("Name='bobby', Age=29", set.getBody());
		assertEquals("SET Name='bobby', Age=29", set.getClause());
	}
	
	public void testCreateWithMap() throws Exception {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Name", "'bobby'");
		map.put("Age", "29");
		Clause set = new SetClause(map);
		assertEquals("set", set.getType());
		assertEquals("Name='bobby', Age=29", set.getBody());
		assertEquals("SET Name='bobby', Age=29", set.getClause());
	}

}
