package zoedb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zoedb.connection.ConnectionPool;
import zoedb.connection.DBConnection;
import zoedb.result.Result;

public class InsertStatement implements SQLStatement {
	
	private final String tableName;
	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	
	static {
		SQLStatementFactory.getInstance().registerStatementType("insert", InsertStatement.class);
	}
	
	public InsertStatement(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getType() {
		return "insert";
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}

	@Override
	public String getStatement() {
		Clause insert = null;
		Clause values = null;
		for (Clause clause : clauses) {
			if(clause.getType().equalsIgnoreCase("insert")) {
				insert = clause;
			} else if(clause.getType().equalsIgnoreCase("values")) {
				values = clause;
			}
		}
		
		return String.format("%s %s;", insert.getClause(), values.getClause());
	}

	@Override
	public void addClause(String clauseType, String body) {
		try {
			ClauseFactory factory = ClauseFactory.getInstance();
			if(clauseType.equalsIgnoreCase("insert")) {
				clauses.add(factory.getClause(clauseType, this.tableName, body));
			} else {
				clauses.add(factory.getClause(clauseType, body));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addClause(String clauseType, List list) {
		try {
			ClauseFactory factory = ClauseFactory.getInstance();
			if(clauseType.equalsIgnoreCase("insert")) {
				clauses.add(factory.getClause(clauseType, this.tableName, list));
			} else {
				clauses.add(factory.getClause(clauseType, list));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addClause(String clauseType, Map map) {
		//TODO: Figure out a way to eliminate this empty function.
	}
	
	public void addClause(String clauseType, SQLStatement stmt) {
		//TODO: Figure out a way to eliminate this empty function.
	}

	public Result execute() {
		ConnectionPool pool = ConnectionPool.getInstance();
		DBConnection con = pool.getConnection("standard");
		Result result = con.execute(this);
		pool.releaseConnection(con);
		return result;
	}
}
