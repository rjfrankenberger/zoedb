package unittests.clausetests;

import java.util.ArrayList;

import junit.framework.TestCase;
import zoedb.Clause;
import zoedb.OrderByClause;

public class TestOrderByClause extends TestCase {
	
	public void testCreateWithString() throws Exception {
		Clause orderBy = new OrderByClause("date");
		
		assertEquals("order by", orderBy.getType());
		assertEquals("date", orderBy.getBody());
		assertEquals("ORDER BY date", orderBy.getClause());
	}
	
	public void testCreateWithModifier() throws Exception {
		Clause orderBy = new OrderByClause("date ASC");
		
		assertEquals("order by", orderBy.getType());
		assertEquals("date ASC", orderBy.getBody());
		assertEquals("ORDER BY date ASC", orderBy.getClause());
	}

	public void testCreateWithList() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		list.add("city ASC");
		list.add("state DESC");
		list.add("country");
		Clause orderBy = new OrderByClause(list);
		
		assertEquals("order by", orderBy.getType());
		assertEquals("city ASC, state DESC, country", orderBy.getBody());
		assertEquals("ORDER BY city ASC, state DESC, country", orderBy.getClause());
	}
}
