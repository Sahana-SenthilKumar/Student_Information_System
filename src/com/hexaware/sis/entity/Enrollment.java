package com.hexaware.sis.entity;

import java.sql.Date;

public class Enrollment {
	
	// Field
	
    private int enrollmentId;
	private String studentId;
    private int courseId;
    private Date enrollmentDate;
	

    // Constructor
    
	public Enrollment(int enrollmentId, String studentId, int courseId, Date enrollmentDate) {
		super();
		this.enrollmentId = enrollmentId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.enrollmentDate = enrollmentDate;
	}

	public Enrollment(String studentId, int courseId, Date enrollmentDate) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
		this.enrollmentDate = enrollmentDate;
	}
	
	public Enrollment() {
		
	}
    
    // Getter
    
    public int getEnrollmentId() {
		return enrollmentId;
	}
    
	public String getStudentId() {
		return studentId;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}
    
    
    // Setter
	
	public void setEnrollmentId(int enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
    
    
    // to.String()
	
    @Override
	public String toString() {
		return "Enrollment [enrollmentId=" + enrollmentId + ", studentId=" + studentId + ", courseId=" + courseId
				+ ", enrollmentDate=" + enrollmentDate + "]";
	}
    
    

}
