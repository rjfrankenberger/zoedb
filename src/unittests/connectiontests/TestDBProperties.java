package unittests.connectiontests;

import zoedb.connection.DBProperties;
import junit.framework.TestCase;

public class TestDBProperties extends TestCase {
	
	public void testProperties() throws Exception {
		DBProperties props = DBProperties.getProperties();
		DBProperties propsAgain = DBProperties.getProperties();
		assertTrue(props.equals(propsAgain));
		assertEquals("standard", props.getProperty("type"));
		assertEquals("com.mysql.jdbc.Driver", props.getProperty("driver"));
		assertEquals("root", props.getProperty("dbuser"));
		assertEquals("490sqlcapstone", props.getProperty("dbpass"));
		assertEquals("127.0.0.1", props.getProperty("dbhost"));
		assertEquals("3306", props.getProperty("dbport"));
	}
	
	public void testPropertiesPassConnectionName() throws Exception {
		DBProperties props = DBProperties.getProperties();
		assertEquals("standard", props.getProperty("DEFAULT", "type"));
		assertEquals("com.mysql.jdbc.Driver", props.getProperty("DEFAULT", "driver"));
		assertEquals("root", props.getProperty("DEFAULT", "dbuser"));
		assertEquals("490sqlcapstone", props.getProperty("DEFAULT", "dbpass"));
		assertEquals("127.0.0.1", props.getProperty("DEFAULT", "dbhost"));
		assertEquals("3306", props.getProperty("DEFAULT", "dbport"));
	}
	
	public void testPropertiesTwoConnections() throws Exception {
		DBProperties props = DBProperties.getProperties();
		assertEquals("standard", props.getProperty("DEFAULT", "type"));
		assertEquals("com.mysql.jdbc.Driver", props.getProperty("DEFAULT", "driver"));
		assertEquals("root", props.getProperty("DEFAULT", "dbuser"));
		assertEquals("490sqlcapstone", props.getProperty("DEFAULT", "dbpass"));
		assertEquals("127.0.0.1", props.getProperty("DEFAULT", "dbhost"));
		assertEquals("3306", props.getProperty("DEFAULT", "dbport"));

		assertEquals("ssh", props.getProperty("secondary", "type"));
		assertEquals("com.mysql.jdbc.Driver", props.getProperty("secondary", "driver"));
		assertEquals("easynode", props.getProperty("secondary", "dbuser"));
		assertEquals("easy", props.getProperty("secondary", "dbpass"));
		assertEquals("localhost", props.getProperty("secondary", "dbhost"));
		assertEquals("3366", props.getProperty("secondary", "dbport"));
	}
}
