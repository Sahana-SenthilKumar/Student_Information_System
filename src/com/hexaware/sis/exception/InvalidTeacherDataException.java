package com.hexaware.sis.exception;

public class InvalidTeacherDataException extends Exception{
	
	public InvalidTeacherDataException(String msg) {
		super(msg);
	}
	
	public InvalidTeacherDataException() {
		super("Invalid Teacher Data.\n");
	}

}
