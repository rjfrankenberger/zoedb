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
package zoedb.connection;

import java.util.Arrays;

import zoedb.SQLStatement;
import zoedb.result.Result;

public class Connection {
	
	private ConnectionPool pool;
	
	public Connection() {
		pool = ConnectionPool.getInstance();
	}
	
	public Result executeStatement(SQLStatement stmt) {
		DBConnection con = pool.getConnection("standard");
		return con.execute(stmt);
	}

}
