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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import zoedb.exception.NoMoreConnectionsAvailableException;
import zoedb.exception.TypeNotRegisteredException;
import zoedb.util.SingleLogHandler;

public class ConnectionPool {
	
	private static ConnectionPool instance;
	private static HashMap registeredConnectionTypes = new HashMap();
	private static int maxPoolSize;
	private static final int DEFAULT_MAX_CONNECTIONS = 10;
	private HashMap<String,ArrayList<DBConnection>> pool;
	
	private ConnectionPool() {
		this.pool = new HashMap<String,ArrayList<DBConnection>>();
		this.updatePoolWithNewRegisteredConnectionTypes();
	}
	
	public static ConnectionPool getInstance() {
		if(instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	public DBConnection getConnection(String type) throws NoMoreConnectionsAvailableException {
		DBConnection returnConnection = null;
		if(pool.containsKey(type)) {
			ArrayList<DBConnection> connections = pool.get(type);
			for (DBConnection con : connections) {
				if(con.isAvailable()) {
					returnConnection = con;
					break;
				}
			}
			if(returnConnection != null) {
				returnConnection.setUnavailable();
			} else {
				throw new NoMoreConnectionsAvailableException(type);
			}
		} else {
			
		}
		System.out.println(String.format("\n\tCONNECTION ACQUIRED(%d): \n\t\ttype='%s' : Pool Size=%d, Busy(%d), Avail(%d)",
				returnConnection.hashCode(),
				type, 
				pool.get(type).size(),
				(pool.get(type).size() - getNumberOfAvailableConnections(type)),
				getNumberOfAvailableConnections(type)));
		return returnConnection;
	}
	
	public void releaseConnection(DBConnection con) {
		con.close();
		con.setAvailable();
		System.out.println(String.format("\n\tCONNECTION RELEASED(%d): \n\t\ttype='%s' : Pool Size=%d, Busy(%d), Avail(%d)",
				con.hashCode(),
				con.getType(),
				pool.get(con.getType()).size(),
				(pool.get(con.getType()).size() - getNumberOfAvailableConnections(con.getType())),
				getNumberOfAvailableConnections(con.getType())));
	}
	
	public int getNumberOfAvailableConnections(String type) {
		int numAvailable = 0;
		if(pool.containsKey(type)) {
			ArrayList<DBConnection> connections = pool.get(type);
			for (DBConnection con : connections) {
				if(con.isAvailable()) {
					numAvailable++;
				}
			}
		}
		return numAvailable;
	}
	
	public void registerConnectionType(String typeName, Class connectionClass) {
		registeredConnectionTypes.put(typeName, connectionClass);
		this.updatePoolWithNewRegisteredConnectionTypes();
	}
	
	private void updatePoolWithNewRegisteredConnectionTypes() {
		String maxConnections = DBProperties.getProperties().getProperty("maxconnections");
		maxPoolSize = (maxConnections == null) ? DEFAULT_MAX_CONNECTIONS : Integer.parseInt(maxConnections);
		Set entrySet = registeredConnectionTypes.entrySet();
		Iterator iter = entrySet.iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String connectionType = (String) entry.getKey();
			Class connectionClass = (Class) entry.getValue();
			if(!pool.containsKey(connectionType)) {
				ArrayList<DBConnection> connections = new ArrayList<DBConnection>(5);
				Constructor[] constructors = connectionClass.getConstructors();
				Constructor constructor = constructors[0];
				for(int i = 0; i < maxPoolSize; i++) {
					try {
						connections.add((DBConnection)constructor.newInstance());
					} catch (InstantiationException e) {
//						logger.severe(e.toString());
						e.printStackTrace();
					} catch (IllegalAccessException e) {
//						logger.severe(e.toString());
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
//						logger.severe(e.toString());
						e.printStackTrace();
					} catch (InvocationTargetException e) {
//						logger.severe(e.toString());
						e.printStackTrace();
					}
				}
				pool.put(connectionType, connections);
			}
		}
	}

}
