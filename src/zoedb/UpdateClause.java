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

public class UpdateClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("update", UpdateClause.class);
	}
	
	public UpdateClause(String table) {
		table = (table.contains(".")) ? table : 
			DBProperties.getProperties().getProperty("defaultschema") + "." + table;
		this.body = table;
	}

	@Override
	public String getType() {
		return "update";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "UPDATE " + this.body;
	}

}
