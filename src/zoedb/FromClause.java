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

import zoedb.connection.DBProperties;

public class FromClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("from", FromClause.class);
	}
	
	public FromClause(String clauseBody) {
		if(clauseBody.contains(".")) {
			this.body = clauseBody;
		} else {
			this.body = DBProperties.getProperties().getProperty("defaultschema") + "." + clauseBody;
		}
	}
	
	public FromClause(SQLStatement nestedStmt) {
		String nested = nestedStmt.getStatement().substring(0, nestedStmt.getStatement().length() - 1);
		this.body = "(" + nested + ")";
	}

	@Override
	public String getType() {
		return "from";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "FROM " + this.body;
	}

}
