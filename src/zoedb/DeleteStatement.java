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
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zoedb.connection.ConnectionPool;
import zoedb.connection.DBConnection;
import zoedb.connection.DBProperties;
import zoedb.exception.NullObjectException;
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
	
	public DeleteStatement(JSONObject json) {
		String table = "";
		try {
			table = json.getString("table");
			for (String fieldName : JSONObject.getNames(json)) {
				if(fieldName.equalsIgnoreCase("where")) {
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
//			logger.severe(e.toString());
			e.printStackTrace();
		}
		this.tableName = table;
	}

	@Override
	public String getType() {
		return "delete";
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
		ArrayList<Clause> wheres = new ArrayList<Clause>();
		String defaultSchema = DBProperties.getProperties().getProperty("defaultschema");
		for (Clause clause : clauses) {
			if(clause.getType().equalsIgnoreCase("where")) {
				wheres.add(clause);
			}
		}
		
		String whereClause = wheres.get(0).getClause();
		for(int i = 1; i < wheres.size(); i++) {
			whereClause += " AND " + wheres.get(i).getBody();
		}
		
		return String.format("DELETE FROM %s %s;", 
				(defaultSchema == null || this.tableName.contains(".")) ? 
						this.tableName : defaultSchema + "." + this.tableName, 
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
	
	@Override
	public void addClause(String clauseType, String body, String mod)
			throws NullObjectException {
		// TODO Auto-generated method stub
		
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
		Result result = new Result();
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			DBConnection con = pool.getConnection("standard");
			result = con.execute(this);
			pool.releaseConnection(con);
		} catch (Exception e) {
//			logger.severe(e.toString());
			e.printStackTrace();
		}
		return result;
	}

	

}
