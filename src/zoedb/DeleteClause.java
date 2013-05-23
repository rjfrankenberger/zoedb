package zoedb;

public class DeleteClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("delete", DeleteClause.class);
	}
	
	public DeleteClause(String table) {
		this.body = table;
	}

	@Override
	public String getType() {
		return "delete";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "DELETE FROM " + this.body;
	}

}
