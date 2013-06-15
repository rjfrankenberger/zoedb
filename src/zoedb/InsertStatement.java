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
					ArrayList<String> columns = new ArrayList<String>();
					String values = "";
					for(int i = 0; i < insertArray.length(); i++) {
						JSONObject insert = insertArray.getJSONObject(i);
						columns.add(insert.getString("attribute"));
						values += (insert.get("value") instanceof String) ? "'" + insert.getString("value") + "'" : insert.get("value");
						values += ", ";
					}
					values = values.substring(0, values.length() - 2);
					this.addClause("insert", columns);
					this.addClause("values", values);
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
