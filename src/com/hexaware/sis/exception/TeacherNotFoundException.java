package com.hexaware.sis.exception;

public class TeacherNotFoundException extends Exception{
	
	public TeacherNotFoundException(String msg) {
		super(msg);
	}
	
	public TeacherNotFoundException() {
		super("Teacher Not Found.\n");
	}

}
