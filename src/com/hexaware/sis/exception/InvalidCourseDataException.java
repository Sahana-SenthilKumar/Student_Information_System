package com.hexaware.sis.exception;

public class InvalidCourseDataException extends Exception{
	
	public InvalidCourseDataException(String msg) {
		super(msg);
	}
	
	public InvalidCourseDataException() {
		super("Invalid Course Data.\n");
	}

}
