package zoedb;

public class UpdateClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("update", UpdateClause.class);
	}
	
	public UpdateClause(String table) {
		this.body = table;
	}

	@Override
	public String getType() {
		return "update";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "UPDATE " + this.body;
	}

}
