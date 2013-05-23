package zoedb;

public class WhereClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("where", WhereClause.class);
	}
	
	public WhereClause(String clauseBody) {
		this.body = clauseBody;
	}

	@Override
	public String getType() {
		return "where";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "WHERE " + this.body;
	}

}
