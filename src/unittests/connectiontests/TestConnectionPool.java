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
		assertEquals(5, pool.getNumberOfAvailableConnections("standard"));
		DBConnection con = pool.getConnection("standard");
		DBConnection con2 = pool.getConnection("standard");
		DBConnection con3 = pool.getConnection("standard");
		assertEquals(2, pool.getNumberOfAvailableConnections("standard"));
		DBConnection con4 = pool.getConnection("standard");
		DBConnection con5 = pool.getConnection("standard");
		assertEquals(0, pool.getNumberOfAvailableConnections("standard"));
		assertTrue(con instanceof StandardConnection);
		pool.releaseConnection(con);
		assertEquals(1, pool.getNumberOfAvailableConnections("standard"));
		DBConnection con6 = pool.getConnection("standard");
		try {
			DBConnection con7 = pool.getConnection("standard");
		} catch (Exception e) {
			assertTrue(e instanceof NoMoreConnectionsAvailableException);
		}
	}

}
