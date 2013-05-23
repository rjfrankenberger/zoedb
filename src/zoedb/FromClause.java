package zoedb;

public class FromClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("from", FromClause.class);
	}
	
	public FromClause(String clauseBody) {
		this.body = clauseBody;
	}
	
	public FromClause(SQLStatement nestedStmt) {
		String nested = nestedStmt.getStatement().substring(0, nestedStmt.getStatement().length() - 1);
		this.body = "(" + nested + ")";
	}

	@Override
	public String getType() {
		return "from";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "FROM " + this.body;
	}

}
