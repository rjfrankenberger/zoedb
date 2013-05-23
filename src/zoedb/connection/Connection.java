package zoedb.connection;

import java.util.Arrays;

import zoedb.SQLStatement;
import zoedb.result.Result;

public class Connection {
	
	private ConnectionPool pool;
	
	public Connection() {
		pool = ConnectionPool.getInstance();
	}
	
	public Result executeStatement(SQLStatement stmt) {
		DBConnection con = pool.getConnection("standard");
		return con.execute(stmt);
	}

}
