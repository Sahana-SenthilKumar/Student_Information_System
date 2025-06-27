package com.hexaware.sis.exception;

public class InvalidEnrollmentDataException extends Exception{
	
	public InvalidEnrollmentDataException(String msg) {
		super(msg);
	}
	
	public InvalidEnrollmentDataException() {
		super("Invalid Enrollment Data.\n");
	}

}
