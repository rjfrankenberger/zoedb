package zoedb.exception;

public class NoMoreConnectionsAvailableException extends Exception {
	
	public NoMoreConnectionsAvailableException() {
		super("There are no more available connections.");
	}
	
	public NoMoreConnectionsAvailableException(String type) {
		super(String.format("There are no more connections available of type '%s'.", type));
	}

}
