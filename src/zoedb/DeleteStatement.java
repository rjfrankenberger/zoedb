package zoedb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zoedb.connection.ConnectionPool;
import zoedb.connection.DBConnection;
import zoedb.result.Result;

public class DeleteStatement implements SQLStatement {
	
	private final String tableName;
	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	
	static {
		SQLStatementFactory.getInstance().registerStatementType("delete", DeleteStatement.class);
	}
	
	public DeleteStatement(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getType() {
		return "delete";
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}

	@Override
	public String getStatement() {
		ArrayList<Clause> wheres = new ArrayList<Clause>();
		for (Clause clause : clauses) {
			if(clause.getType().equalsIgnoreCase("where")) {
				wheres.add(clause);
			}
		}
		
		String whereClause = wheres.get(0).getClause();
		for(int i = 1; i < wheres.size(); i++) {
			whereClause += " AND " + wheres.get(i).getBody();
		}
		
		return String.format("DELETE FROM %s %s;", this.tableName, whereClause);
	}

	@Override
	public void addClause(String clauseType, String body) {
		try {
			ClauseFactory factory = ClauseFactory.getInstance();
			clauses.add(factory.getClause(clauseType, body));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addClause(String clauseType, List expressionElements) {
		// TODO Figure out a way to eliminate this empty function.
	}

	@Override
	public void addClause(String clauseType, Map map) {
		// TODO Figure out a way to eliminate this empty function.
	}

	@Override
	public void addClause(String clauseType, SQLStatement nestedStmt) {
		// TODO Figure out a way to eliminate this empty function.
	}
	
	@Override
	public Result execute() {
		ConnectionPool pool = ConnectionPool.getInstance();
		DBConnection con = pool.getConnection("standard");
		Result result = con.execute(this);
		pool.releaseConnection(con);
		return result;
	}

}
