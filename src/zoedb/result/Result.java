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
import java.util.Iterator;
import java.util.List;

public class Result implements Iterable<ResultRow>{
	
	List<String> attributes;
	ArrayList<ResultRow> rows = new ArrayList<ResultRow>();
	
	public Result() {
		this.attributes = new ArrayList<String>();
	}
	
	public Result(List<String> attributes) {
		this.attributes = attributes;
	}
	
	public int getNumberOfColumns() {
		return attributes.size();
	}
	
	public List<String> getAttributeList() {
		return this.attributes;
	}
	
	public void insert(ResultRow row) {
		rows.add(row);
	}

	public Iterator<ResultRow> iterator() {
		return rows.iterator();
	}
	
	public int size() {
		return this.rows.size();
	}
	
	public String JSONString() {
		String json = "[";
		if(this.size() > 0) {
			List<String> attributes = this.getAttributeList();
			
			for (ResultRow row : this) {
				json += "{";
				for (String attr : attributes) {
					json += "\"" + attr + "\" : ";
					json += (row.get(attr).getValue() instanceof String) ? "\"" + row.get(attr).getValue() + "\"" : row.get(attr).getValue();
					json += ", ";
				} 
				json = json.substring(0, json.length() - 2 ) + "}, ";
			}
			
			return json.substring(0, json.length() - 2) + "]";
		}
		return "{}";
	}
}
