package pl.shockah.jay;

import javax.annotation.Nonnull;

public class JSONParseException extends RuntimeException {
	private static final long serialVersionUID = 3759792316306049206L;
	
	public JSONParseException() {
		super();
	}
	
	public JSONParseException(@Nonnull String message) {
		super(message);
	}
	
	public JSONParseException(@Nonnull Throwable throwable) {
		super(throwable);
	}
}