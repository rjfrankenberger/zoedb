package unittests.connectiontests;

import zoedb.connection.ConnectionPool;
import zoedb.connection.DBConnection;
import zoedb.connection.StandardConnection;
import zoedb.exception.NoMoreConnectionsAvailableException;
import junit.framework.TestCase;

public class TestConnectionPool extends TestCase {

	public void testGetInstance() {
		ConnectionPool pool1 = ConnectionPool.getInstance();
		ConnectionPool pool2 = ConnectionPool.getInstance();
		assertTrue(pool1.equals(pool2));
	}
	
	public void testGetAndReleaseStandardConnection() throws Exception {
		zoedb.util.TypeLoader.loadTypes();
		ConnectionPool pool = ConnectionPool.getInstance();
		assertEquals(10, pool.getNumberOfAvailableConnections("standard"));
		DBConnection con = pool.getConnection("standard");
		DBConnection con2 = pool.getConnection("standard");
		DBConnection con3 = pool.getConnection("standard");
		assertEquals(7, pool.getNumberOfAvailableConnections("standard"));
		DBConnection con4 = pool.getConnection("standard");
		DBConnection con5 = pool.getConnection("standard");
		assertEquals(5, pool.getNumberOfAvailableConnections("standard"));
		assertTrue(con instanceof StandardConnection);
		pool.releaseConnection(con);
		assertEquals(6, pool.getNumberOfAvailableConnections("standard"));
	}

}
