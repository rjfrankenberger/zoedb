package unittests.testobjects;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import zoedb.SQLStatement;
import zoedb.exception.NullObjectException;
import zoedb.result.Result;

public class TestStatement implements SQLStatement {

	@Override
	public String getType() {
		return "test";
	}

	@Override
	public String getTableName() {
		return "TestTable";
	}

	@Override
	public String getStatement() {
		return "TEST STATEMENT;";
	}

	@Override
	public void addClause(String clauseType, String body) {
		// DO NOTHING
	}

	@Override
	public void addClause(String clauseType, List expressionElements) {
		// DO NOTHING
	}

	@Override
	public void addClause(String clauseType, Map map) {
		// DO NOTHING
	}

	@Override
	public void addClause(String clauseType, SQLStatement nestedStmt) {
		// DO NOTHING
	}

	@Override
	public Result execute() {
		return new Result(Arrays.asList("column1", "column2", "column3"));
	}

	@Override
	public void addClause(String clauseType, String body, String mod)
			throws NullObjectException {
		// TODO Auto-generated method stub
		
	}

}
