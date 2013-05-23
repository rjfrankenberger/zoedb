package zoedb.connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import zoedb.SQLStatement;
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
			Result result = null;
			ArrayList<String> columnNames = new ArrayList<String>();
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
						if(java.sql.Types.INTEGER == md.getColumnType(i)) {
							System.out.println(md.getColumnTypeName(i));
						}
					}
					result = new Result(columnNames);
					while(rs.next()) {
//						System.out.println(rs.getRow());
						ResultRow row = new ResultRow();
						for (String col : columnNames) {
							row.add(new RowEntry(col, rs.getString(col)));
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
			}
			return result;
	}
	
}
