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

public class JoinClause implements Clause {
	
	private final String body;
	private final String mod;
	
	static {
		ClauseFactory.getInstance().registerClauseType("join", JoinClause.class);
	}
	
	public JoinClause(String clauseBody) {
		this.body = clauseBody;
		this.mod = null;
	}
	
	public JoinClause(String clauseBody, String mod) {
		this.body = clauseBody;
		this.mod = mod;
	}

	@Override
	public String getType() {
		return "join";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		String clause;
		if(this.mod != null) {
			clause = this.mod.toUpperCase() + " JOIN " + this.body; 
		} else {
			clause = "JOIN " + this.body;
		}
		return clause;
	}

}
