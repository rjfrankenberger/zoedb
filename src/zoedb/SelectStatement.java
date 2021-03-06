/*
 * This file is part of zoedb.

 *  zoedb is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  zoedb is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with zoedb.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  Copyright 2013 Robert Frankenberger
 */

package zoedb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import zoedb.connection.ConnectionPool;
import zoedb.connection.DBConnection;
import zoedb.exception.NullObjectException;
import zoedb.result.Result;

public class SelectStatement implements SQLStatement {
	
	private final String tableName;
	private String nestedAlias = null;
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
	
	public SelectStatement(JSONObject json) {
		String table = "";
		try {
			for (String fieldName : JSONObject.getNames(json)) {
				if(fieldName.equalsIgnoreCase("table")) {
					if(json.get("table") instanceof JSONObject) {
						JSONObject nestedJSON = json.getJSONObject("table");
						table = "nested";
						this.nestedAlias = nestedJSON.getString("table alias");
						SQLStatement nested = SQLStatementFactory.getInstance().getSQLStatement("select", nestedJSON);
						clauses.add(ClauseFactory.getInstance().getClause("from", nested));
					} else {
						table = json.getString("table");
						clauses.add(ClauseFactory.getInstance().getClause("from", table));
					}
				} else if(fieldName.equalsIgnoreCase("select")) {
					this.addClause(fieldName, json.getString(fieldName));
				} else if(fieldName.equalsIgnoreCase("join")) {
					JSONArray joinArray = json.getJSONArray("join");
					for(int i = 0; i < joinArray.length(); i++) {
						JSONObject join = joinArray.getJSONObject(i);
						if(join.has("mod")) {
							this.addClause("join", join.getString("table") + " ON " 
									+ join.getString("lhs") + "=" + join.getString("rhs"), join.getString("mod"));
						} else {
							this.addClause("join", join.getString("table") + " ON " 
													+ join.getString("lhs") + "=" + join.getString("rhs"));
						}
					}
				} else if(fieldName.equalsIgnoreCase("where")) {
					JSONArray whereArray = json.getJSONArray("where");
					for(int i = 0; i < whereArray.length(); i++) {
						JSONObject where = whereArray.getJSONObject(i);
						String expression = "";
						expression += where.getString("attribute");
						expression += (where.has("op")) ? where.getString("op") : "=";
						expression += (where.get("value") instanceof String) ? "'" + where.getString("value") + "'" : where.get("value");
						this.addClause("where", expression);
					}
				} else if(fieldName.equalsIgnoreCase("order by")) {
					JSONArray orderByArray = json.getJSONArray("order by");
					ArrayList<String> instructions = new ArrayList<String>();
					for(int i = 0; i < orderByArray.length(); i++) {
						JSONObject orderBy = orderByArray.getJSONObject(i);
						instructions.add(orderBy.getString("instruction"));
					}
					this.addClause("order by", instructions);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tableName = table;
	}

	@Override
	public String getType() {
		return "select";
	}

	@Override
	public String getTableName() {
		String[] schemaAndTable = null;
		if(tableName.contains(".")) {
			schemaAndTable = tableName.split("\\.");
		}
		return (schemaAndTable == null) ? this.tableName : schemaAndTable[1];
	}

	@Override
	public String getStatement() {
		ClauseFactory factory = ClauseFactory.getInstance();
		Clause select = Clause.NULL;
		Clause from = Clause.NULL;
		ArrayList<Clause> joins = new ArrayList<Clause>();
		ArrayList<Clause> wheres = new ArrayList<Clause>();
		Clause orderBy = Clause.NULL;
		for (Clause clause : clauses) {
			if(clause.getType().equalsIgnoreCase("select")) {
				select = clause;
			} else if(clause.getType().equalsIgnoreCase("from")) {
				from = clause;
			} else if(clause.getType().equalsIgnoreCase("join")) {
				joins.add(clause);
			} else if(clause.getType().equalsIgnoreCase("where")) {
				wheres.add(clause);
			} else if(clause.getType().equalsIgnoreCase("order by")) {
				orderBy = clause;
			}
		}
		if(select.equals(Clause.NULL)) {
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
		
		String joinClause = "";
		for(int i = 0; i < joins.size(); i++) {
			joinClause += joins.get(i).getClause() + " ";
		}
		
		return String.format("%s %s %s%s%s;", select.getClause(), 
											 (this.tableName.equalsIgnoreCase("nested")) ? 
													 from.getClause() + " AS "  + this.nestedAlias : from.getClause(), 
											 joinClause,
											 whereClause,
											 (orderBy.equals(Clause.NULL)) ? "" : " " + orderBy.getClause());
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
	public void addClause(String clauseType, String body, String mod) throws NullObjectException {
		try {
			ClauseFactory factory = ClauseFactory.getInstance();
			clauses.add(factory.getClause(clauseType, body, mod));
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
	
	@Override
	public Result execute() {
		Result result = new Result();
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			DBConnection con = pool.getConnection("standard");
			result = con.execute(this);
			pool.releaseConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
