package zoedb;

import java.util.List;

public class SelectClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("select", SelectClause.class);
	}
	
	public SelectClause(String clauseBody) {
		this.body = clauseBody;
	}
	
	public SelectClause(List expressionList) {
		String tempBody = "";
		for (Object column : expressionList) {
			tempBody += (String) column + ", ";
		}
		this.body = tempBody.substring(0, tempBody.length() - 2);
	}

	@Override
	public String getType() {
		return "select";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "SELECT " + this.body;
	}

}
