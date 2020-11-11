package ua.foxminded.yakovlev.university.exception;

public class CantUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CantUpdateException() {
		super();
	}

	public CantUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public CantUpdateException(String message) {
		super(message);
	}

	public CantUpdateException(Throwable cause) {
		super(cause);
	}
}
