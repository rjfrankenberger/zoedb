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
import org.json.JSONException;
import org.json.JSONObject;

import zoedb.connection.ConnectionPool;
import zoedb.connection.DBConnection;
import zoedb.result.Result;

public class InsertStatement implements SQLStatement {
	
	private String tableName;
	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	
	static {
		SQLStatementFactory.getInstance().registerStatementType("insert", InsertStatement.class);
	}
	
	public InsertStatement(String tableName) {
		this.tableName = tableName;
	}
	
	public InsertStatement(JSONObject json) {
		try {
			this.tableName = json.getString("table");
			for (String fieldName : JSONObject.getNames(json)) {
				if(fieldName.equalsIgnoreCase("insert")) {
					JSONArray insertArray = json.getJSONArray("insert");
					ArrayList<String> columns = new ArrayList<String>();;
					String values = "";
					ArrayList<Clause> valuesList = new ArrayList<Clause>();
					for(int i = 0; i < insertArray.length(); i++) {
						if(insertArray.get(i) instanceof JSONArray) {
							JSONArray innerArray = insertArray.getJSONArray(i);
							columns = new ArrayList<String>();
							for(int j = 0; j < innerArray.length(); j++) {
								JSONObject insert = innerArray.getJSONObject(j);
								columns.add(insert.getString("attribute"));
								values += (insert.get("value") instanceof String) ? "'" + insert.getString("value") + "'" : insert.get("value");
								values += ", ";
							}
							try {
								values = values.substring(0, values.length() - 2);
								valuesList.add(ClauseFactory.getInstance().getClause("values", values));
							} catch (Exception e) {
								e.printStackTrace();
							}
							values = "";
						} else {
							JSONObject insert = insertArray.getJSONObject(i);
							columns.add(insert.getString("attribute"));
							values += (insert.get("value") instanceof String) ? "'" + insert.getString("value") + "'" : insert.get("value");
							values += ", ";
						}
					}
					this.addClause("insert", columns);
					if(values.length() > 0) {
						values = values.substring(0, values.length() - 2);
						this.addClause("values", values);
					} else {
						this.addClause("values", valuesList);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(this.tableName == null) {
			this.tableName = "";
		}
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
		Clause insert = Clause.NULL;
		Clause values = Clause.NULL;
		ArrayList<Clause> valuesList = new ArrayList<Clause>();
		for (Clause clause : clauses) {
			if(clause.getType().equalsIgnoreCase("insert")) {
				insert = clause;
			} else if(clause.getType().equalsIgnoreCase("values")) {
				valuesList.add(clause);
			}
		}
		
		try {
			values = ClauseFactory.getInstance().getClause("values", valuesList);
		} catch (Exception e) {
			e.printStackTrace();
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
		
		try {
			SQLStatement select = SQLStatementFactory.getInstance().getSQLStatement("select", this.tableName);
			String[] columns = null;
			ArrayList<String[]> values = new ArrayList<String[]>();
			for (Clause clause : this.clauses) {
				String body = clause.getBody();
				if(clause.getType().equalsIgnoreCase("insert")) {
					body = body.substring(body.indexOf('(') + 1, body.lastIndexOf(')'));
					columns = body.split(", ");
				} else if (clause.getType().equalsIgnoreCase("values")) {
					String[] tuples = body.split("\\),\\(");
					body = tuples[0].substring(1);
					values.add(body.split(", "));
					for(int i = 1; i < tuples.length - 1; i++) {
						body = tuples[i];
						values.add(body.split(", "));
					}
					String lastTuple = tuples[tuples.length - 1];
					body = lastTuple.substring(0, lastTuple.length() - 1);
					values.add(body.split(", "));
				}
			}
			
			for(int i = 0; i < columns.length; i++) {
				String whereBody = "(";
				for (String[] row : values) {
					whereBody += columns[i] + "=" + row[i] + " OR ";
				}
				whereBody = whereBody.substring(0, whereBody.length() - 4) + ")";
				select.addClause("where", whereBody);
			}
			result = select.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		pool.releaseConnection(con);
		return result;
	}
}
