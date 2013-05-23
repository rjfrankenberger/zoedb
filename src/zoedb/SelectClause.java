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

public class SelectClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("select", SelectClause.class);
	}
	
	public SelectClause(String clauseBody) {
		this.body = clauseBody;
	}
	
	public SelectClause(List expressionList) {
		String tempBody = "";
		for (Object column : expressionList) {
			tempBody += (String) column + ", ";
		}
		this.body = tempBody.substring(0, tempBody.length() - 2);
	}

	@Override
	public String getType() {
		return "select";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "SELECT " + this.body;
	}

}
