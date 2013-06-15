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

public class UpdateStatement implements SQLStatement {
	
	private final String tableName;
	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	
	static {
		SQLStatementFactory.getInstance().registerStatementType("update", UpdateStatement.class);
	}

	public UpdateStatement(String tableName) {
		this.tableName = tableName;
	}
	
	public UpdateStatement(JSONObject json) {
		String table = "";
		try {
			table = json.getString("table");
			for (String fieldName : JSONObject.getNames(json)) {
				if(fieldName.equalsIgnoreCase("set")) {
					JSONArray setArray = json.getJSONArray("set");
					ArrayList<String> list = new ArrayList<String>();
					for(int i = 0; i < setArray.length(); i++) {
						JSONObject set = setArray.getJSONObject(i);
						String expression = set.getString("attribute") + "=";
						expression += (set.get("value") instanceof String) ? "'" + set.getString("value") + "'" : set.get("value");
						list.add(expression);
					}
					this.addClause("set", list);
				} else if(fieldName.equalsIgnoreCase("where")) {
					JSONArray whereArray = json.getJSONArray("where");
					for(int i = 0; i < whereArray.length(); i++) {
						JSONObject where = whereArray.getJSONObject(i);
						String expression = where.getString("attribute") + "=";
						expression += (where.get("value") instanceof String) ? "'" + where.getString("value") + "'" : where.get("value");
						this.addClause("where", expression);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.tableName = table;
	}
	
	@Override
	public String getType() {
		return "update";
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}

	@Override
	public String getStatement() {
		Clause set = Clause.NULL;
		ArrayList<Clause> wheres = new ArrayList<Clause>();
		for (Clause clause : clauses) {
			if(clause.getType().equalsIgnoreCase("update")) {
				
			} else if(clause.getType().equalsIgnoreCase("set")) {
				set = clause;
			} else if(clause.getType().equalsIgnoreCase("where")) {
				wheres.add(clause);
			}
		}
		
		String whereClause = wheres.get(0).getClause();
		for(int i = 1; i < wheres.size(); i++) {
			whereClause += " AND " + wheres.get(i).getBody();
		}
		
		return String.format("UPDATE %s %s %s;", this.tableName,
										  set.getClause(),
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
	
	public void addClause(String clauseType, List list) {
		try {
			ClauseFactory factory = ClauseFactory.getInstance();
			clauses.add(factory.getClause(clauseType, list));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addClause(String clauseType, Map map) {
		try {
			ClauseFactory factory = ClauseFactory.getInstance();
			clauses.add(factory.getClause(clauseType, map));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
