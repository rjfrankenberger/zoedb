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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("set", SetClause.class);
	}
	
	public SetClause(String body) {
		this.body = body;
	}

	public SetClause(List list) {
		String tempBody = "";
		for (Object item : list) {
			tempBody += (String) item + ", ";
		}
		this.body = tempBody.substring(0, tempBody.length() - 2);
	}
	
	public SetClause(Map map) {
		String tempBody = "";
		Set entrySet = map.entrySet();
		Iterator iter = entrySet.iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			tempBody += String.format("%s=%s, ", (String) entry.getKey(), (String) entry.getValue());
		}
		this.body = tempBody.substring(0, tempBody.length() - 2);
	}

	@Override
	public String getType() {
		return "set";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "SET " + this.body;
	}

}
