package unittests.connectiontests;

import zoedb.connection.DBProperties;
import junit.framework.TestCase;

public class TestDBProperties extends TestCase {
	
	public void testProperties() throws Exception {
		DBProperties props = DBProperties.getProperties();
		DBProperties propsAgain = DBProperties.getProperties();
		assertTrue(props.equals(propsAgain));
		assertEquals("standard", props.getProperty("type"));
		assertEquals("10", props.getProperty("maxconnections"));
		assertEquals("com.mysql.jdbc.Driver", props.getProperty("driver"));
		assertEquals("root", props.getProperty("dbuser"));
		assertEquals("password", props.getProperty("dbpass"));
		assertEquals("localhost", props.getProperty("dbhost"));
		assertEquals("3306", props.getProperty("dbport"));
		assertEquals("sakila", props.getProperty("defaultschema"));
	}
	
	public void testPropertiesPassConnectionName() throws Exception {
		DBProperties props = DBProperties.getProperties();
		assertEquals("standard", props.getProperty("DEFAULT", "type"));
		assertEquals("10", props.getProperty("DEFAULT", "maxconnections"));
		assertEquals("com.mysql.jdbc.Driver", props.getProperty("DEFAULT", "driver"));
		assertEquals("root", props.getProperty("DEFAULT", "dbuser"));
		assertEquals("password", props.getProperty("DEFAULT", "dbpass"));
		assertEquals("localhost", props.getProperty("DEFAULT", "dbhost"));
		assertEquals("3306", props.getProperty("DEFAULT", "dbport"));
		assertEquals("sakila", props.getProperty("DEFAULT", "defaultschema"));
	}
	
	public void testPropertiesTwoConnections() throws Exception {
		DBProperties props = DBProperties.getProperties();
		assertEquals("standard", props.getProperty("DEFAULT", "type"));
		assertEquals("10", props.getProperty("DEFAULT", "maxconnections"));
		assertEquals("com.mysql.jdbc.Driver", props.getProperty("DEFAULT", "driver"));
		assertEquals("root", props.getProperty("DEFAULT", "dbuser"));
		assertEquals("password", props.getProperty("DEFAULT", "dbpass"));
		assertEquals("localhost", props.getProperty("DEFAULT", "dbhost"));
		assertEquals("3306", props.getProperty("DEFAULT", "dbport"));
		assertEquals("sakila", props.getProperty("DEFAULT", "defaultschema"));

		assertEquals("ssh", props.getProperty("secondary", "type"));
		assertEquals("10", props.getProperty("secondary", "maxconnections"));
		assertEquals("com.mysql.jdbc.Driver", props.getProperty("secondary", "driver"));
		assertEquals("easynode", props.getProperty("secondary", "dbuser"));
		assertEquals("easy", props.getProperty("secondary", "dbpass"));
		assertEquals("localhost", props.getProperty("secondary", "dbhost"));
		assertEquals("3366", props.getProperty("secondary", "dbport"));
		assertEquals("sakila", props.getProperty("secondary", "defaultschema"));
	}
}
