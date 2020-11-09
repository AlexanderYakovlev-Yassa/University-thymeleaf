package ua.foxminded.yakovlev.university.exception;

public class ServiceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceNotFoundException() {
		super();
	}

	public ServiceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceNotFoundException(String message) {
		super(message);
	}

	public ServiceNotFoundException(Throwable cause) {
		super(cause);
	}
}
