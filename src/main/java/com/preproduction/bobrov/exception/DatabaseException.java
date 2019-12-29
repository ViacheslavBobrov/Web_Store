package com.preproduction.bobrov.exception;

public class DatabaseException extends RuntimeException{

	private static final long serialVersionUID = -6895507605086227478L;

	public DatabaseException() {
		
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);		
	}

	public DatabaseException(String message) {
		super(message);		
	}	
	
}
