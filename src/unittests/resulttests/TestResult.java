package unittests.resulttests;

import java.util.Arrays;

import junit.framework.TestCase;
import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.result.Result;
import zoedb.result.ResultRow;
import zoedb.result.RowEntry;

public class TestResult extends TestCase {
	
	private Result r;
	
	public void setUp() {
		r = new Result(Arrays.asList("column1", "column2", "column3"));
		ResultRow row1 = new ResultRow();
		row1.add(new RowEntry("column1", 1));
		row1.add(new RowEntry("column2", "row1"));
		row1.add(new RowEntry("column3", 1.11));
		ResultRow row2 = new ResultRow();
		row2.add(new RowEntry("column1", 2));
		row2.add(new RowEntry("column2", "row2"));
		row2.add(new RowEntry("column3", 2.22));
		ResultRow row3 = new ResultRow();
		row3.add(new RowEntry("column1", 3));
		row3.add(new RowEntry("column2", "row3"));
		row3.add(new RowEntry("column3", 3.33));
		r.insert(row1);
		r.insert(row2);
		r.insert(row3);
	}
	
	public void testResultConstructor() {
		setUp();
		assertEquals(3, r.getNumberOfColumns());
		int i = 1;
		for (String col : r.getAttributeList()) {
			assertEquals(String.format("column%d", i), col);
			i++;
		}
	}
	
	public void testAccessRows() {
		int i = 1;
		for (ResultRow row : r) {
			assertEquals(1*i, row.get("column1").getValue());
			assertEquals(String.format("row%d", i), row.get("column2").getValue());
			assertEquals((1.11*i), row.get("column3").getValue());
			i++;
		}
	}
	
	public void testSize() throws Exception {
		setUp();
		assertEquals(3, r.size());
	}
	
	public void testJSONString() throws Exception {
		String testString = "[" +
							   "{\"column1\" : 1, \"column2\" : \"row1\", \"column3\" : 1.11}, " +
							   "{\"column1\" : 2, \"column2\" : \"row2\", \"column3\" : 2.22}, " +
							   "{\"column1\" : 3, \"column2\" : \"row3\", \"column3\" : 3.33}" +
							  "]";
		assertEquals(testString, r.JSONString());
	}
	
	public void testJSONStringActual() throws Exception {
		SQLStatementFactory factory = SQLStatementFactory.getInstance();
		SQLStatement select = factory.getSQLStatement("select", "language");
		Result result = select.execute();
		String testString = "[" +
								"{\"language_id\" : 1, \"name\" : \"English\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 2, \"name\" : \"Italian\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 3, \"name\" : \"Japanese\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 4, \"name\" : \"Mandarin\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 5, \"name\" : \"French\", \"last_update\" : \"2006-02-15 05:02:19\"}, " +
								"{\"language_id\" : 6, \"name\" : \"German\", \"last_update\" : \"2006-02-15 05:02:19\"}" +
							"]";
		assertEquals(testString, result.JSONString());
	}

}
