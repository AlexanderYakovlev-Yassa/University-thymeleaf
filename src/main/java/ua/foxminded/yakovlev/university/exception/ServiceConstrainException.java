package ua.foxminded.yakovlev.university.exception;

public class ServiceConstrainException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceConstrainException() {
		super();
	}

	public ServiceConstrainException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceConstrainException(String message) {
		super(message);
	}

	public ServiceConstrainException(Throwable cause) {
		super(cause);
	}
}
