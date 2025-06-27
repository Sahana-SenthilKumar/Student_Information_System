package com.hexaware.sis.exception;

public class StudentNotFoundException extends Exception{
	
	public StudentNotFoundException(String msg) {
		super(msg);
	}
	
	public StudentNotFoundException() {
		super("Student Not Found.\n");
	}

}
