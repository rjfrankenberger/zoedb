package unittests.statementtests;

import zoedb.SQLStatement;
import zoedb.exception.NullObjectException;
import zoedb.exception.TypeNotRegisteredException;
import junit.framework.TestCase;

public class TestSQLStatement extends TestCase {
	
	public void testNull() {
		assertEquals("", SQLStatement.NULL.getType());
		assertEquals("", SQLStatement.NULL.getTableName());
		assertEquals("", SQLStatement.NULL.getStatement());
		assertEquals(0, SQLStatement.NULL.execute().getAttributeList().size());
		assertEquals(0, SQLStatement.NULL.execute().getNumberOfColumns());		
	}
	
	public void testNullAddClause() throws Exception {
		SQLStatement stmt = SQLStatement.NULL;
		try {
			stmt.addClause("select", "*");
		} catch (Exception e) {
			assertTrue(e instanceof NullObjectException);
		}
	}

}
