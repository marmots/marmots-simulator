package org.marmots.exceptions;

public class SimulatorException extends RuntimeException {
	/**
	 * generated uid
	 */
	private static final long serialVersionUID = 6688832755785010921L;

	public SimulatorException() {
		super();
	}

	public SimulatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SimulatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public SimulatorException(String message) {
		super(message);
	}

	public SimulatorException(Throwable cause) {
		super(cause);
	}
}
