package com.losy.common.exception;

public class PermissionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6798215041984368465L;

	private String message;

	
	
	
	public PermissionException() {
		super();
	}




	public PermissionException(String message, Throwable cause) {
		super(message, cause);
	}




	public PermissionException(String message) {
		super(message);
		this.message = message;
	}




	public PermissionException(Throwable cause) {
		super(cause);
	}




	public String getMessage() {
		return message;
	}
	
}
