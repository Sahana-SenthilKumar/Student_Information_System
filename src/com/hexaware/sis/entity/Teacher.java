package com.hexaware.sis.entity;

//import java.util.*;

public class Teacher {
	
	// Field

	private String teacherId;
    private String firstName;
    private String lastName;
    private String email;
    
    
    // Constructor
    
    public Teacher(String teacherId, String firstName, String lastName, String email) {
		super();
		this.teacherId = teacherId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
    
    //Getter
    
	public String getTeacherId() {
		return teacherId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	
	// Setter
	
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// to.String()
	@Override
	public String toString() {
		return "Teacher [teacherId=" + teacherId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + "]";
	}
	   
	

}
