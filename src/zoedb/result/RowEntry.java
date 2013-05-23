package zoedb.result;

public class RowEntry<T> {
	
	private String attribute;
	private T value;
	
	public RowEntry(String attr, T value) {
		this.attribute = attr;
		this.value = value;
	}
	
	public String getAttribute() {
		return this.attribute;
	}
	
	public T getValue() {
		return this.value;
	}

}
