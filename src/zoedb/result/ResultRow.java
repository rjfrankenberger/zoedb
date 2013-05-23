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

package zoedb.result;

import java.util.ArrayList;

public class ResultRow {
	
	private ArrayList<RowEntry> values = new ArrayList<RowEntry>();
	
	public ResultRow() {
	}
	
	public void add(RowEntry entry) {
		values.add(entry);
	}
	
	public RowEntry get(String attr) {
		RowEntry returnEntry = null;
		for (RowEntry entry : values) {
			if(entry.getAttribute().equalsIgnoreCase(attr)) {
				returnEntry = entry;
			}
		}
		return returnEntry;
	}

}
