package zoedb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zoedb.connection.ConnectionPool;
import zoedb.connection.DBConnection;
import zoedb.result.Result;

public class SelectStatement implements SQLStatement {
	
	private final String tableName;
	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	
	static {
		SQLStatementFactory.getInstance().registerStatementType("select", SelectStatement.class);
	}
	
	public SelectStatement(String tableName)  {
		this.tableName = tableName;
		try {
			clauses.add(ClauseFactory.getInstance().getClause("from", tableName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getType() {
		return "select";
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}

	@Override
	public String getStatement() {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause select = null;
		Clause from = null;
		Clause join = null;
		ArrayList<Clause> wheres = new ArrayList<Clause>();
		for (Clause clause : clauses) {
			if(clause.getType().equalsIgnoreCase("select")) {
				select = clause;
			} else if(clause.getType().equalsIgnoreCase("from")) {
				from = clause;
			} else if(clause.getType().equalsIgnoreCase("join")) {
				join = clause;
			} else if(clause.getType().equalsIgnoreCase("where")) {
				wheres.add(clause);
			}
		}
		if(select == null) {
			try {
				select = factory.getClause("select", "*");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String whereClause = wheres.size() > 0 ? wheres.get(0).getClause() : "";
		for(int i = 1; i < wheres.size(); i++) {
			whereClause += " AND " + wheres.get(i).getBody();
		}
		
		return String.format("%s %s %s%s;", select.getClause(), 
											 from.getClause(), 
											 join == null ? "" : join.getClause() + " ",
											 whereClause);
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
	
	public void addClause(String clauseType, List expressionElements) {
		try {
			ClauseFactory factory = ClauseFactory.getInstance();
			clauses.add(factory.getClause(clauseType, expressionElements));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addClause(String clauseType, Map map) {
		//TODO: Figure out a way to eliminate this empty function.
	}
	
	public void addClause(String clauseType, SQLStatement nestedStmt) {
		try {
			ClauseFactory factory = ClauseFactory.getInstance();
			clauses.add(factory.getClause(clauseType, nestedStmt));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Result execute() {
		ConnectionPool pool = ConnectionPool.getInstance();
		DBConnection con = pool.getConnection("standard");
		Result result = con.execute(this);
		pool.releaseConnection(con);
		return result;
	}

}
