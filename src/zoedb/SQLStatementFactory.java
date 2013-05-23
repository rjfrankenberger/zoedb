package zoedb;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import zoedb.exception.TypeNotRegisteredException;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SQLStatementFactory {
	
	private static SQLStatementFactory instance;
	private static HashMap registeredStatementTypes = new HashMap();
	
	public static SQLStatementFactory getInstance() {
		if(instance == null) {
			instance = new SQLStatementFactory();
		}
		return instance;
	}
	
	public void registerStatementType(String typeName, Class statementClass) {
		registeredStatementTypes.put(typeName, statementClass);
	}

	public SQLStatement getSQLStatement(String stmtType, String tableName) throws Exception {
		Class statementClass = (Class) registeredStatementTypes.get(stmtType);
		if(statementClass == null) {
			throw new TypeNotRegisteredException();
		}
		Constructor statementConstructor = statementClass.getDeclaredConstructor(new Class[] {String.class});
		return (SQLStatement) statementConstructor.newInstance(tableName);
	}
	
	public SQLStatement getSQLStatement(String stmtType) throws Exception {
		return getSQLStatement(stmtType, null);
	}
}
