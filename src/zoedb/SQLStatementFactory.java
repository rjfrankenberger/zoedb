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
			throw new TypeNotRegisteredException(stmtType, this.getClass());
		}
		Constructor statementConstructor = statementClass.getDeclaredConstructor(new Class[] {String.class});
		return (SQLStatement) statementConstructor.newInstance(tableName);
	}
	
	public SQLStatement getSQLStatement(String stmtType) throws Exception {
		return getSQLStatement(stmtType, null);
	}
}
