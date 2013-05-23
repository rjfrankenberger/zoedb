package zoedb.result;

import java.util.ArrayList;

public class ResultRow {
	
	private ArrayList<RowEntry> values = new ArrayList<RowEntry>();
	
	public ResultRow() {
	}
	
	public void add(RowEntry entry) {
		values.add(entry);
	}
	
	public RowEntry get(String attr) {
		RowEntry returnEntry = null;
		for (RowEntry entry : values) {
			if(entry.getAttribute().equalsIgnoreCase(attr)) {
				returnEntry = entry;
			}
		}
		return returnEntry;
	}

}
