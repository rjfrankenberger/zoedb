package zoedb.connection;

import zoedb.SQLStatement;
import zoedb.result.Result;

public interface DBConnection {
	
	public boolean isAvailable();
	public void setUnavailable();
	public void setAvailable();
	public Result execute(SQLStatement stmt);

}
