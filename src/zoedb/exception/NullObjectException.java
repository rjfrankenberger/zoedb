package zoedb.exception;

public class NullObjectException extends Exception {
	
	public NullObjectException() {
		super("Attempting to make changes to a constructed NULL object");
	}
}
