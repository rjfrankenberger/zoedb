package zoedb.exception;

public class TypeNotRegisteredException extends Exception {
	
	public TypeNotRegisteredException() {
		super("The statement type has not been registered with the SQLStatementFactory");
	}
	
	public TypeNotRegisteredException(String message) {
		super(message);
	}

}
