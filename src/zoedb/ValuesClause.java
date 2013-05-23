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

public class ValuesClause implements Clause {
	
	public final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("values", ValuesClause.class);
	}
	
	public ValuesClause(String values) {
		this.body = String.format("(%s)", values);
	}
	
	public ValuesClause(List values) {
		String tempBody = "(";
		for (Object value : values) {
			tempBody += value + ", ";
		}
		this.body = tempBody.substring(0, tempBody.length() - 2) + ")";
	}

	@Override
	public String getType() {
		return "values";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return String.format("VALUES%s", this.body);
	}

}
