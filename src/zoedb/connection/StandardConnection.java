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

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import zoedb.SQLStatement;
import zoedb.SQLStatementFactory;
import zoedb.result.Result;
import zoedb.result.ResultRow;
import zoedb.result.RowEntry;

public class StandardConnection implements DBConnection {
	
	private static DBProperties props = DBProperties.getProperties();
	private boolean isAvailable;
	private java.sql.Connection con = null;
	
	static {
		ConnectionPool.getInstance().registerConnectionType("standard", StandardConnection.class);
	}
	
	public StandardConnection() {
		isAvailable = true;
		String driver = props.getProperty("driver");
		String hostname = props.getProperty("dbhost");
		String user = props.getProperty("dbuser");
		String pass = props.getProperty("dbpass");
		try {
			Class.forName(driver);
			this.con = DriverManager.getConnection("jdbc:mysql://" + hostname, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isAvailable() {
		return this.isAvailable;
	}
	
	@Override
	public void setUnavailable() {
		this.isAvailable = false;
	}
	
	@Override
	public void setAvailable() {
		this.isAvailable = true;
	}
	
	@Override
	public Result execute(SQLStatement stmt) {
			System.out.println(stmt.getStatement());
			Result result = new Result();
			ArrayList<String> columnNames = new ArrayList<String>();
			ArrayList<Integer> columnTypes = new ArrayList<Integer>();
			ResultSet rs = null;
			ResultSetMetaData md = null;
			try {
				Statement statement = this.con.createStatement();
				if(stmt.getType().equalsIgnoreCase("select")) {
					rs = statement.executeQuery(stmt.getStatement());
					md = rs.getMetaData();
					// get column names to pass to Result constructor
					for(int i = 1; i <= md.getColumnCount(); i++) {
						columnNames.add(md.getColumnName(i));
						columnTypes.add(md.getColumnType(i));
					}
					result = new Result(columnNames);
					while(rs.next()) {
						ResultRow row = new ResultRow();
						for(int i = 0; i < columnNames.size(); i++) {
							switch(columnTypes.get(i)) {
								case Types.INTEGER:
								{
									row.add(new RowEntry(columnNames.get(i), rs.getInt(columnNames.get(i))));
									break;
								}
								case Types.VARCHAR:
								{
									row.add(new RowEntry(columnNames.get(i), rs.getString(columnNames.get(i))));
									break;
								}
								case Types.TIMESTAMP:
								{
									Timestamp t = rs.getTimestamp(columnNames.get(i));
									java.util.Date d = t;
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									String date = sdf.format(d);
									row.add(new RowEntry(columnNames.get(i), date));
									break;
								}
								default:
								{
									row.add(new RowEntry(columnNames.get(i), rs.getString(columnNames.get(i))));
								}
							}
						}
						result.insert(row);
					}
				} else if(stmt.getType().equalsIgnoreCase("insert")) {
					statement.execute(stmt.getStatement());
				} else if(stmt.getType().equalsIgnoreCase("update")) {
					statement.executeUpdate(stmt.getStatement());
				} else if(stmt.getType().equalsIgnoreCase("delete")) {
					statement.execute(stmt.getStatement());
				} else {
					// throw an exception 
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return result;
	}
	
}
