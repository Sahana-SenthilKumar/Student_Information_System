package com.hexaware.sis.exception;

public class DuplicateEnrollmentException extends Exception{
	
	public DuplicateEnrollmentException(String msg) {
		super(msg);
	}
	
	public DuplicateEnrollmentException() {
		super("Duplicate Enrollment.\n");
	}

}
