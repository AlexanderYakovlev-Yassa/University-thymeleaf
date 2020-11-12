package ua.foxminded.yakovlev.university.exception;

public class ConstrainException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConstrainException() {
		super();
	}

	public ConstrainException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConstrainException(String message) {
		super(message);
	}

	public ConstrainException(Throwable cause) {
		super(cause);
	}
}
