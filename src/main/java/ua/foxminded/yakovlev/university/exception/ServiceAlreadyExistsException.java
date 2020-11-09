package ua.foxminded.yakovlev.university.exception;

public class ServiceAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceAlreadyExistsException() {
		super();
	}

	public ServiceAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceAlreadyExistsException(String message) {
		super(message);
	}

	public ServiceAlreadyExistsException(Throwable cause) {
		super(cause);
	}
}
