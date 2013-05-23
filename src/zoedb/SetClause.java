package zoedb;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetClause implements Clause {
	
	private final String body;
	
	static {
		ClauseFactory.getInstance().registerClauseType("set", SetClause.class);
	}
	
	public SetClause(String body) {
		this.body = body;
	}

	public SetClause(List list) {
		String tempBody = "";
		for (Object item : list) {
			tempBody += (String) item + ", ";
		}
		this.body = tempBody.substring(0, tempBody.length() - 2);
	}
	
	public SetClause(Map map) {
		String tempBody = "";
		Set entrySet = map.entrySet();
		Iterator iter = entrySet.iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			tempBody += String.format("%s=%s, ", (String) entry.getKey(), (String) entry.getValue());
		}
		this.body = tempBody.substring(0, tempBody.length() - 2);
	}

	@Override
	public String getType() {
		return "set";
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getClause() {
		return "SET " + this.body;
	}

}
