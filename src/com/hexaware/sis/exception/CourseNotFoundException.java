package com.hexaware.sis.exception;

public class CourseNotFoundException extends Exception{
	
	public CourseNotFoundException(String msg) {
		super(msg);
	}
	
	public CourseNotFoundException() {
		super("Course Not Found.\n");
	}

}
