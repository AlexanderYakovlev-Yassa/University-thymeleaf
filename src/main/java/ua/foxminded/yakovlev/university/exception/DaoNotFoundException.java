package ua.foxminded.yakovlev.university.exception;

public class DaoNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public DaoNotFoundException() {
		super();
	}

	public DaoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoNotFoundException(String message) {
		super(message);
	}

	public DaoNotFoundException(Throwable cause) {
		super(cause);
	}
}
