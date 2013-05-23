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
	
	public static ClauseFactory getInstance() {
		if(instance == null) {
			instance = new ClauseFactory();
		}
		return instance;
	}
	
	public void registerClauseType(String typeName, Class clauseClass) {
		registeredClauseTypes.put(typeName, clauseClass);
	}

	public Clause getClause(String clauseType, String clauseBody) throws Exception {
		Class clauseClass = (Class) registeredClauseTypes.get(clauseType);
		if(clauseClass == null) {
			throw new TypeNotRegisteredException();
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
			throw new TypeNotRegisteredException();
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
			throw new TypeNotRegisteredException();
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
			throw new TypeNotRegisteredException();
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
			throw new TypeNotRegisteredException();
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
			throw new TypeNotRegisteredException();
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
