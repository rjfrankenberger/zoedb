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

package zoedb.util;

public class TypeLoader {
	
	public static void loadTypes() {
		try {
			Class.forName("zoedb.DeleteClause");
			Class.forName("zoedb.DeleteStatement");
			Class.forName("zoedb.FromClause");
			Class.forName("zoedb.InsertClause");
			Class.forName("zoedb.InsertStatement");
			Class.forName("zoedb.JoinClause");
			Class.forName("zoedb.OrderByClause");
			Class.forName("zoedb.SelectClause");
			Class.forName("zoedb.SelectStatement");
			Class.forName("zoedb.SetClause");
			Class.forName("zoedb.UpdateClause");
			Class.forName("zoedb.UpdateStatement");
			Class.forName("zoedb.ValuesClause");
			Class.forName("zoedb.WhereClause");
			Class.forName("zoedb.connection.StandardConnection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
