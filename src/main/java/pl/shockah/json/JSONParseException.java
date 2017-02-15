package pl.shockah.json;

public class JSONParseException extends RuntimeException {
	private static final long serialVersionUID = 3759792316306049206L;
	
	public JSONParseException() {
		super();
	}
	
	public JSONParseException(String message) {
		super(message);
	}
	
	public JSONParseException(Throwable throwable) {
		super(throwable);
	}
}