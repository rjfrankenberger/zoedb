package zoedb;

public class JoinClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("join", JoinClause.class);
	}
	
	public JoinClause(String clauseBody) {
		this.body = clauseBody;
	}

	@Override
	public String getType() {
		return "join";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "JOIN " + this.body;
	}

}
