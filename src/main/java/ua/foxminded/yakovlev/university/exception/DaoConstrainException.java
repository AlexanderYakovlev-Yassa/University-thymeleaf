package ua.foxminded.yakovlev.university.exception;

public class DaoConstrainException extends Exception {

	private static final long serialVersionUID = 1L;

	public DaoConstrainException() {
		super();
	}

	public DaoConstrainException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoConstrainException(String message) {
		super(message);
	}

	public DaoConstrainException(Throwable cause) {
		super(cause);
	}
}
