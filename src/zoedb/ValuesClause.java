package zoedb;

import java.util.List;

public class ValuesClause implements Clause {
	
	public final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("values", ValuesClause.class);
	}
	
	public ValuesClause(String values) {
		this.body = String.format("(%s)", values);
	}
	
	public ValuesClause(List values) {
		String tempBody = "(";
		for (Object value : values) {
			tempBody += value + ", ";
		}
		this.body = tempBody.substring(0, tempBody.length() - 2) + ")";
	}

	@Override
	public String getType() {
		return "values";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return String.format("VALUES%s", this.body);
	}

}
