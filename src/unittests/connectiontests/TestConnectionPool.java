package unittests.connectiontests;

import zoedb.connection.ConnectionPool;
import zoedb.connection.DBConnection;
import zoedb.connection.StandardConnection;
import junit.framework.TestCase;

public class TestConnectionPool extends TestCase {
	
	public void setUp() {
		try {
			Class.forName("zoedb.connection.StandardConnection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetInstance() {
		ConnectionPool pool1 = ConnectionPool.getInstance();
		ConnectionPool pool2 = ConnectionPool.getInstance();
		assertTrue(pool1.equals(pool2));
	}
	
	public void testGetAndReleaseStandardConnection() {
		setUp();
		ConnectionPool pool = ConnectionPool.getInstance();
		assertEquals(5, pool.getNumberOfAvailableConnections("standard"));
		DBConnection con = pool.getConnection("standard");
		assertEquals(4, pool.getNumberOfAvailableConnections("standard"));
		assertTrue(con instanceof StandardConnection);
		pool.releaseConnection(con);
//		assertNull(con);
		assertEquals(5, pool.getNumberOfAvailableConnections("standard"));
	}

}
