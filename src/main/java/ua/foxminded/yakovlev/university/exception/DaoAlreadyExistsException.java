package ua.foxminded.yakovlev.university.exception;

public class DaoAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public DaoAlreadyExistsException() {
		super();
	}

	public DaoAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoAlreadyExistsException(String message) {
		super(message);
	}

	public DaoAlreadyExistsException(Throwable cause) {
		super(cause);
	}
}
