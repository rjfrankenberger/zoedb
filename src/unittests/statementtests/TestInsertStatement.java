package unittests.statementtests;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.json.JSONObject;

import zoedb.InsertStatement;
import zoedb.SQLStatement;

public class TestInsertStatement extends TestCase {

	public void testCreateWithColumnString() throws Exception {
		SQLStatement insert = new InsertStatement("TestTable");
		insert.addClause("insert", "column1, column2, column3");
		insert.addClause("values", "value1, value2, value3");
		assertEquals("insert", insert.getType());
		assertEquals("TestTable", insert.getTableName());
		assertEquals("INSERT INTO sakila.TestTable (column1, column2, column3) " +
					 "VALUES(value1, value2, value3);", insert.getStatement());
	}
	
	public void testCreateWithColumnList() throws Exception {
		SQLStatement insert = new InsertStatement("TestTable");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("column1");
		columns.add("column2");
		columns.add("column3");
		insert.addClause("insert", columns);
		insert.addClause("values", "value1, value2, value3");
		assertEquals("insert", insert.getType());
		assertEquals("TestTable", insert.getTableName());
		assertEquals("INSERT INTO sakila.TestTable (column1, column2, column3) " +
					 "VALUES(value1, value2, value3);", insert.getStatement());
	}
	
	public void testCreateWithMultipleRows() throws Exception {
		SQLStatement insert = new InsertStatement("TestTable");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("column1");
		columns.add("column2");
		columns.add("column3");
		insert.addClause("insert", columns);
		insert.addClause("values", "value1, value2, value3");
		insert.addClause("values", "value4, value5, value6");
		assertEquals("insert", insert.getType());
		assertEquals("TestTable", insert.getTableName());
		assertEquals("INSERT INTO sakila.TestTable (column1, column2, column3) " +
					 "VALUES(value1, value2, value3)," +
					       "(value4, value5, value6);", insert.getStatement());
	}
	
	public void testCreateWithExplicitTable() throws Exception {
		SQLStatement insert = new InsertStatement("test.TestTable");
		insert.addClause("insert", "column1, column2, column3");
		insert.addClause("values", "value1, value2, value3");
		assertEquals("insert", insert.getType());
		assertEquals("TestTable", insert.getTableName());
		assertEquals("INSERT INTO test.TestTable (column1, column2, column3) " +
					 "VALUES(value1, value2, value3);", insert.getStatement());
	}
	
	public void testCreateWithJSONObject() throws Exception {
		String jsonString = "{'type' : 'INSERT', " +
				 "'table' : 'tableName'," +
				 "'insert' : [" +
				            "{'attribute' : 'attr1', 'value' : 'val1'}," +
				            "{'attribute' : 'attr2', 'value' : 'val2'}" +
				           "]" +
				"}";
		JSONObject json = new JSONObject(jsonString);
		SQLStatement insert = new InsertStatement(json);
		
		assertEquals("insert", insert.getType());
		assertEquals("tableName", insert.getTableName());
		assertEquals("INSERT INTO sakila.tableName (attr1, attr2) " +
					 "VALUES('val1', 'val2');", insert.getStatement());
	}
	
//	public void testExecute() throws Exception {
//		SQLStatementFactory factory = SQLStatementFactory.getInstance();
//		SQLStatement insert = factory.getSQLStatement("insert", "test.mytable");
//		insert.addClause("insert", "firstname, lastname");
//		insert.addClause("values", "'zoe', 'frankenberger'");
//		Result result = insert.execute();
//		assertNotNull(result);
//		assertEquals(0, result.getNumberOfColumns());
//	}
	
	public void testExecuteActualLaptopMultiple() throws Exception {
//		String insert1 = "{" +
//				"\"type\":\"INSERT\"," +
//				"\"table\":\"photostore.screen_photos\"," +
//				"\"insert\":[" +
//						"{\"attribute\":\"filename\",\"value\":\"20130115_221150.JPG\"}," +
//						"{\"attribute\":\"datetaken\",\"value\":\"2013-01-15 22:11:50\"}," +
//						"{\"attribute\":\"city\",\"value\":\"columbia\"}," +
//						"{\"attribute\":\"state\",\"value\":\"south carolina\"}," +
//						"{\"attribute\":\"country\",\"value\":\"USA\"}," +
//						"{\"attribute\":\"event\",\"value\":\"\"}," +
//						"{\"attribute\":\"uploadedby\",\"value\":\"bobby\"}," +
//						"{\"attribute\":\"dateuploaded\",\"value\":\"2013-06-20 09:38:00\"}" +
//							"]" +
//						"}:";
//		
//		String insert2 = "{" +
//				"\"type\":\"INSERT\"," +
//				"\"table\":\"photostore.screen_photos\"," +
//				"\"insert\":[" +
//				           "[" +
//						"{\"attribute\":\"filename\",\"value\":\"20130115_221150.JPG\"}," +
//						"{\"attribute\":\"datetaken\",\"value\":\"2013-01-15 22:11:50\"}," +
//						"{\"attribute\":\"city\",\"value\":\"columbia\"}," +
//						"{\"attribute\":\"state\",\"value\":\"south carolina\"}," +
//						"{\"attribute\":\"country\",\"value\":\"USA\"}," +
//						"{\"attribute\":\"event\",\"value\":\"\"}," +
//						"{\"attribute\":\"uploadedby\",\"value\":\"bobby\"}," +
//						"{\"attribute\":\"dateuploaded\",\"value\":\"2013-06-20 09:38:00\"}" +
//						   "]," +
//						   "[" +
//						"{\"attribute\":\"filename\",\"value\":\"20130115_221201.JPG\"}," +
//						"{\"attribute\":\"datetaken\",\"value\":\"2013-01-15 22:12:01\"}," +
//						"{\"attribute\":\"city\",\"value\":\"columbia\"}," +
//						"{\"attribute\":\"state\",\"value\":\"south carolina\"}," +
//						"{\"attribute\":\"country\",\"value\":\"USA\"}," +
//						"{\"attribute\":\"event\",\"value\":\"\"}," +
//						"{\"attribute\":\"uploadedby\",\"value\":\"bobby\"}," +
//						"{\"attribute\":\"dateuploaded\",\"value\":\"2013-06-20 09:38:00\"}" +
//							"]" +
//							"]" +
//						"}:";
		
//		String insert3 = "{" +
//				"\"type\":\"INSERT\"," +
//				"\"table\":\"photostore.screen_photos\"," +
//				"\"insert\":[" +
//				           "[" +
//						"{\"attribute\":\"filename\",\"value\":\"20130115_221150.JPG\"}," +
//						"{\"attribute\":\"datetaken\",\"value\":\"2013-01-15 22:11:50\"}," +
//						"{\"attribute\":\"city\",\"value\":\"columbia\"}," +
//						"{\"attribute\":\"state\",\"value\":\"south carolina\"}," +
//						"{\"attribute\":\"country\",\"value\":\"USA\"}," +
//						"{\"attribute\":\"event\",\"value\":\"\"}," +
//						"{\"attribute\":\"uploadedby\",\"value\":\"bobby\"}," +
//						"{\"attribute\":\"dateuploaded\",\"value\":\"2013-06-20 09:38:00\"}" +
//						   "]," +
//						   "[" +
//						"{\"attribute\":\"filename\",\"value\":\"20130115_221201.JPG\"}," +
//						"{\"attribute\":\"datetaken\",\"value\":\"2013-01-15 22:12:01\"}," +
//						"{\"attribute\":\"city\",\"value\":\"columbia\"}," +
//						"{\"attribute\":\"state\",\"value\":\"south carolina\"}," +
//						"{\"attribute\":\"country\",\"value\":\"USA\"}," +
//						"{\"attribute\":\"event\",\"value\":\"\"}," +
//						"{\"attribute\":\"uploadedby\",\"value\":\"bobby\"}," +
//						"{\"attribute\":\"dateuploaded\",\"value\":\"2013-06-20 09:38:00\"}" +
//						    "]," +
//						    "[" +
//						"{\"attribute\":\"filename\",\"value\":\"20130518_124807.JPG\"}," +
//						"{\"attribute\":\"datetaken\",\"value\":\"2013-05-18 12:48:07\"}," +
//						"{\"attribute\":\"city\",\"value\":\"columbia\"}," +
//						"{\"attribute\":\"state\",\"value\":\"south carolina\"}," +
//						"{\"attribute\":\"country\",\"value\":\"USA\"}," +
//						"{\"attribute\":\"event\",\"value\":\"\"}," +
//						"{\"attribute\":\"uploadedby\",\"value\":\"bobby\"}," +
//						"{\"attribute\":\"dateuploaded\",\"value\":\"2013-06-20 09:38:00\"}" +
//							"]" +
//							"]" +
//						"}:";
		
//		String insert3 = "{\"type\":\"INSERT\",\"table\":\"photostore.screen_photos\",\"insert\":[[{\"attribute\":\"filename\",\"value\":\"20130408_073722.JPG\"},{\"attribute\":\"datetaken\",\"value\":\"2013-04-08 07:37:22\"},{\"attribute\":\"city\",\"value\":\"a\"},{\"attribute\":\"state\",\"value\":\"a\"},{\"attribute\":\"country\",\"value\":\"a\"},{\"attribute\":\"event\",\"value\":\"a\"},{\"attribute\":\"uploadedby\",\"value\":\"bobby\"},{\"attribute\":\"dateuploaded\",\"value\":\"2013-6-29 11:0:16\"}],[{\"attribute\":\"filename\",\"value\":\"20130408_120406.JPG\"},{\"attribute\":\"datetaken\",\"value\":\"2013-04-08 12:04:06\"},{\"attribute\":\"city\",\"value\":\"d\"},{\"attribute\":\"state\",\"value\":\"d\"},{\"attribute\":\"country\",\"value\":\"d\"},{\"attribute\":\"event\",\"value\":\"d\"},{\"attribute\":\"uploadedby\",\"value\":\"bobby\"},{\"attribute\":\"dateuploaded\",\"value\":\"2013-6-29 11:0:16\"}],[{\"attribute\":\"filename\",\"value\":\"20130408_121652.JPG\"},{\"attribute\":\"datetaken\",\"value\":\"2013-04-08 12:16:52\"},{\"attribute\":\"city\",\"value\":\"fq\"},{\"attribute\":\"state\",\"value\":\"f\"},{\"attribute\":\"country\",\"value\":\"f\"},{\"attribute\":\"event\",\"value\":\"f\"},{\"attribute\":\"uploadedby\",\"value\":\"bobby\"},{\"attribute\":\"dateuploaded\",\"value\":\"2013-6-29 11:0:16\"}]]}:";
//		
//		JSONObject json = new JSONObject(insert3);
//		InsertStatement insert = new InsertStatement(json);
//		System.out.println(insert.getStatement());
//		Result result = insert.execute();
//		System.out.println(result.JSONString());
//		List<String> attributes = result.getAttributeList();
//		Iterator<ResultRow> iter = result.iterator();
//		
//		ResultRow row1 = iter.next();
//		assertTrue(attributes.contains("filename"));
//		assertEquals("20130115_221150.JPG", row1.get("filename").getValue());
//		assertTrue(attributes.contains("datetaken"));
//		assertEquals("2013-01-15 22:11:50", row1.get("datetaken").getValue());
//		assertTrue(attributes.contains("city"));
//		assertEquals("columbia", row1.get("city").getValue());
//		assertTrue(attributes.contains("state"));
//		assertEquals("south carolina", row1.get("state").getValue());
//		assertTrue(attributes.contains("country"));
//		assertEquals("USA", row1.get("country").getValue());
//		assertTrue(attributes.contains("uploadedby"));
//		assertEquals("bobby", row1.get("uploadedby").getValue());
//		
//		ResultRow row2 = iter.next();
//		assertTrue(attributes.contains("filename"));
//		assertEquals("20130115_221201.JPG", row2.get("filename").getValue());
//		assertTrue(attributes.contains("datetaken"));
//		assertEquals("2013-01-15 22:12:01", row2.get("datetaken").getValue());
//		assertTrue(attributes.contains("city"));
//		assertEquals("columbia", row2.get("city").getValue());
//		assertTrue(attributes.contains("state"));
//		assertEquals("south carolina", row2.get("state").getValue());
//		assertTrue(attributes.contains("country"));
//		assertEquals("USA", row2.get("country").getValue());
//		assertTrue(attributes.contains("uploadedby"));
//		assertEquals("bobby", row2.get("uploadedby").getValue());
//		
//		ResultRow row3 = iter.next();
//		assertTrue(attributes.contains("filename"));
//		assertEquals("20130518_124807.JPG", row3.get("filename").getValue());
//		assertTrue(attributes.contains("datetaken"));
//		assertEquals("2013-05-18 12:48:07", row3.get("datetaken").getValue());
//		assertTrue(attributes.contains("city"));
//		assertEquals("columbia", row3.get("city").getValue());
//		assertTrue(attributes.contains("state"));
//		assertEquals("south carolina", row3.get("state").getValue());
//		assertTrue(attributes.contains("country"));
//		assertEquals("USA", row3.get("country").getValue());
//		assertTrue(attributes.contains("uploadedby"));
//		assertEquals("bobby", row3.get("uploadedby").getValue());
	}
	
	
		

}
