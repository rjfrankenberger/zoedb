package zoedb;

import java.util.List;
import java.util.Map;

import zoedb.exception.TypeNotRegisteredException;
import zoedb.result.Result;

public interface SQLStatement {
	
	public String getType();
	public String getTableName();
	public String getStatement();
	public void addClause(String clauseType, String body);
	public void addClause(String clauseType, List expressionElements);
	public void addClause(String clauseType, Map map);
	public void addClause(String clauseType, SQLStatement nestedStmt);
	public Result execute();
}
