package zoedb.connection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ConnectionPool {
	
	private static ConnectionPool instance;
	private static HashMap registeredConnectionTypes = new HashMap();
	private static int maxPoolSize;
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
	
	public DBConnection getConnection(String type) {
		DBConnection returnConnection = null;
		if(pool.containsKey(type)) {
			ArrayList<DBConnection> connections = pool.get(type);
			for (DBConnection con : connections) {
				if(con.isAvailable()) {
					returnConnection = con;
					break;
				}
			}
		}
		returnConnection.setUnavailable();
		return returnConnection;
	}
	
	public void releaseConnection(DBConnection con) {
		con.setAvailable();
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
		maxPoolSize = 5;
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
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				pool.put(connectionType, connections);
			}
		}
	}

}
