package com.losy.common.exception;

public class LoginException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4792781552657208671L;

	public LoginException() {
		super();
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(Throwable cause) {
		super(cause);
	}

	
}
