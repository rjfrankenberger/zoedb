package zoedb.result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Result implements Iterable<ResultRow>{
	
	List<String> attributes;
	ArrayList<ResultRow> rows = new ArrayList<ResultRow>();
	
	public Result(List<String> attributes) {
		this.attributes = attributes;
	}
	
	public int getNumberOfColumns() {
		return attributes.size();
	}
	
	public List<String> getAttributeList() {
		return this.attributes;
	}
	
	public void insert(ResultRow row) {
		rows.add(row);
	}

	public Iterator<ResultRow> iterator() {
		return rows.iterator();
	}
}
