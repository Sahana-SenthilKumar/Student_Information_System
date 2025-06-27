package com.hexaware.sis.exception;

public class InvalidStudentDataException extends Exception{
	
	public InvalidStudentDataException(String msg) {
		super(msg);
	}
	
	public InvalidStudentDataException() {
		super("Invalid Student Data.\n");
	}

}
