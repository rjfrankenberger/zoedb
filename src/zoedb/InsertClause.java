package zoedb;

import java.util.List;

public class InsertClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("insert", InsertClause.class);
	}
	
	public InsertClause(String table, String columns) {
		this.body = String.format("%s (%s)", table, columns);
	}
	
	public InsertClause(String table, List columns) {
		String tempBody = table + " (";
		for (Object column : columns) {
			tempBody += (String) column + ", ";
		}
		this.body = tempBody.substring(0, tempBody.length() - 2) + ")";
	}

	@Override
	public String getType() {
		return "insert";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return String.format("INSERT INTO %s", this.body);
	}

}
