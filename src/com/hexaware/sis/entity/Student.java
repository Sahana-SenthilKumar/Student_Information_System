package com.hexaware.sis.entity;

import java.sql.Date;
//import java.time.LocalDate;
//import java.util.*;


public class Student {
	
	// Field Variables
	
	private String studentId;
	private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private long phoneNumber;
    
	// Constructor
    
    public Student(String studentId, String firstName, String lastName, Date dateOfBirth, String email,
			long phoneNumber) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
    
	// Getter 
    
	public String getStudentId() {
		return studentId;
	}


	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public String getEmail() {
		return email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}
	

	// Setter
	
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	// to.String()
    
    @Override
	public String toString() {
		return "Student [studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}
    
        

}
