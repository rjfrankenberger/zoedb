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
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zoedb.exception.TypeNotRegisteredException;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ClauseFactory {
	private static ClauseFactory instance;
	private static HashMap registeredClauseTypes = new HashMap();
	
	static {
		zoedb.util.TypeLoader.loadTypes();
	}
	
	public static ClauseFactory getInstance() {
		if(instance == null) {
			instance = new ClauseFactory();
		}
		return instance;
	}
	
	public void registerClauseType(String typeName, Class clauseClass) {
		registeredClauseTypes.put(typeName, clauseClass);
	}

	//TODO: condense the various "getClause" functions into a class that follows the template pattern.
	public Clause getClause(String clauseType, String clauseBody) throws Exception {
		Class clauseClass = (Class) registeredClauseTypes.get(clauseType);
		if(clauseClass == null) {
			throw new TypeNotRegisteredException(clauseType, this.getClass());
		}
		Constructor[] clauseConstructors = clauseClass.getDeclaredConstructors();
		Constructor clauseConstructor = null;
		for (Constructor constructor : clauseConstructors) {
			Type[] argTypes = constructor.getGenericParameterTypes();
			if(argTypes.length == 1 && argTypes[0].equals(Class.forName("java.lang.String"))) {
				clauseConstructor = constructor;
			}
		}
		
		return (Clause) clauseConstructor.newInstance(clauseBody);
	}
	
	public Clause getClause(String clauseType, String table, String columns) throws Exception {
		Class clauseClass = (Class) registeredClauseTypes.get(clauseType);
		if(clauseClass == null) {
			throw new TypeNotRegisteredException(clauseType, this.getClass());
		}
		Constructor[] clauseConstructors = clauseClass.getDeclaredConstructors();
		Constructor clauseConstructor = null;
		for (Constructor constructor : clauseConstructors) {
			Type[] argTypes = constructor.getGenericParameterTypes();
			if(argTypes.length == 2 && argTypes[0].equals(Class.forName("java.lang.String"))
									&& argTypes[1].equals(Class.forName("java.lang.String"))) {
				clauseConstructor = constructor;
			}
		}
		
		return (Clause) clauseConstructor.newInstance(table, columns);
	}
	
	public Clause getClause(String clauseType, String table, List columns) throws Exception {
		Class clauseClass = (Class) registeredClauseTypes.get(clauseType);
		if(clauseClass == null) {
			throw new TypeNotRegisteredException(clauseType, this.getClass());
		}
		Constructor[] clauseConstructors = clauseClass.getDeclaredConstructors();
		Constructor clauseConstructor = null;
		for (Constructor constructor : clauseConstructors) {
			Type[] argTypes = constructor.getGenericParameterTypes();
			if(argTypes.length == 2 && argTypes[0].equals(Class.forName("java.lang.String"))
									&& argTypes[1].equals(Class.forName("java.util.List"))) {
				clauseConstructor = constructor;
			}
		}
		
		return (Clause) clauseConstructor.newInstance(table, columns);
	}
	
	public Clause getClause(String clauseType, List expressionList) throws Exception {
		Class clauseClass = (Class) registeredClauseTypes.get(clauseType);
		if(clauseClass == null) {
			throw new TypeNotRegisteredException(clauseType, this.getClass());
		}
		Constructor[] clauseConstructors = clauseClass.getDeclaredConstructors();
		Constructor clauseConstructor = null;
		for (Constructor constructor : clauseConstructors) {
			Type[] argTypes = constructor.getGenericParameterTypes();
			for (Type type : argTypes) {
				if(type.equals(Class.forName("java.util.List"))) {
					clauseConstructor = constructor;
				}
			}
		}
		
		return (Clause) clauseConstructor.newInstance(expressionList);
	}
	
	public Clause getClause(String clauseType, Map map) throws Exception {
		Class clauseClass = (Class) registeredClauseTypes.get(clauseType);
		if(clauseClass == null) {
			throw new TypeNotRegisteredException(clauseType, this.getClass());
		}
		Constructor[] clauseConstructors = clauseClass.getDeclaredConstructors();
		Constructor clauseConstructor = null;
		for (Constructor constructor : clauseConstructors) {
			Type[] argTypes = constructor.getGenericParameterTypes();
			if(argTypes.length == 1 && argTypes[0].equals(Class.forName("java.util.Map"))) {
				clauseConstructor = constructor;
			}
		}
		
		return (Clause) clauseConstructor.newInstance(map);
	}
	
	public Clause getClause(String clauseType, SQLStatement nested) throws Exception {
		Class clauseClass = (Class) registeredClauseTypes.get(clauseType);
		if(clauseClass == null) {
			throw new TypeNotRegisteredException(clauseType, this.getClass());
		}
		Constructor[] clauseConstructors = clauseClass.getDeclaredConstructors();
		Constructor clauseConstructor = null;
		for (Constructor constructor : clauseConstructors) {
			Type[] argTypes = constructor.getGenericParameterTypes();
			for (Type type : argTypes) {
				if(type.equals(Class.forName("zoedb.SQLStatement"))) {
					clauseConstructor = constructor;
				}
			}
		}
		
		return (Clause) clauseConstructor.newInstance(nested);
	}
	
}
