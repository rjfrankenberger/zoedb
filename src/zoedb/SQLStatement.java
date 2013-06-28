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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import zoedb.exception.NullObjectException;
import zoedb.exception.TypeNotRegisteredException;
import zoedb.result.Result;

public interface SQLStatement {
	
	public String getType();
	public String getTableName();
	public String getStatement();
	public void addClause(String clauseType, String body) throws NullObjectException;
	public void addClause(String clauseType, String body, String mod) throws NullObjectException;
	public void addClause(String clauseType, List expressionElements) throws NullObjectException;
	public void addClause(String clauseType, Map map) throws NullObjectException;
	public void addClause(String clauseType, SQLStatement nestedStmt) throws NullObjectException;
	public Result execute();
	
	public static final SQLStatement NULL = new SQLStatement() {

		@Override
		public String getType() {
			return "";
		}

		@Override
		public String getTableName() {
			return "";
		}

		@Override
		public String getStatement() {
			return "";
		}

		@Override
		public void addClause(String clauseType, String body) throws NullObjectException {
			throw new NullObjectException();
			
		}
		
		public void addClause(String clauseType, String body, String mod) throws NullObjectException {
			throw new NullObjectException();
		}

		@Override
		public void addClause(String clauseType, List expressionElements) throws NullObjectException {
			throw new NullObjectException();
			
		}

		@Override
		public void addClause(String clauseType, Map map) throws NullObjectException {
			throw new NullObjectException();
			
		}

		@Override
		public void addClause(String clauseType, SQLStatement nestedStmt) throws NullObjectException {
			throw new NullObjectException();
			
		}

		@Override
		public Result execute() {
			return new Result();
		}
		
	};
}
