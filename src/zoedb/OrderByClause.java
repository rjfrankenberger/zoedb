package zoedb;

import java.util.List;

public class OrderByClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("order by", OrderByClause.class);
	}
	
	public OrderByClause(String body) {
		this.body = body;
	}
	
	public OrderByClause(List list) {
		String theBody = "";
		for (Object item : list) {
			theBody += (String) item + ", ";
		}
		this.body = theBody.substring(0, theBody.length() - 2);
	}

	@Override
	public String getType() {
		return "order by";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return String.format("ORDER BY %s", this.body);
	}

}
