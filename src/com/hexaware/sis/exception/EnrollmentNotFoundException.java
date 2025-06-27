package com.hexaware.sis.exception;

public class EnrollmentNotFoundException extends Exception{
	
    public EnrollmentNotFoundException() {
        super("Enrollment not found.");
    }

    public EnrollmentNotFoundException(String message) {
        super(message);
    }
	

}
