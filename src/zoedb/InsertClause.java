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

import java.util.List;

import zoedb.connection.DBProperties;

public class InsertClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("insert", InsertClause.class);
	}
	
	public InsertClause(String table, String columns) {
		table = (table.contains(".")) ? table : 
			DBProperties.getProperties().getProperty("defaultschema") + "." + table;
		this.body = String.format("%s (%s)", table, columns);
	}
	
	public InsertClause(String table, List columns) {
		table = (table.contains(".")) ? table : 
			DBProperties.getProperties().getProperty("defaultschema") + "." + table;
		String tempBody = table + " (";
		for (Object column : columns) {
			tempBody += (String) column + ", ";
		}
		this.body = tempBody.substring(0, tempBody.length() - 2) + ")";
	}

	@Override
	public String getType() {
		return "insert";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return String.format("INSERT INTO %s", this.body);
	}

}
