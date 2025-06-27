package com.hexaware.sis.entity;

//import java.util.*;

public class Course {

	// Field

	private int courseId;
	private String courseName;
    private int credits;
    private String teacherId;

    
    // Constructor
    
    public Course(int courseId, String courseName, int credits, String teacherId) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.credits = credits;
		this.teacherId = teacherId;
	}
    
    public Course(String courseName, int credits, String teacherId) {
		super();
		this.courseName = courseName;
		this.credits = credits;
		this.teacherId = teacherId;
	}
    
    
    // Getter
    
    
	public int getCourseId() {
		return courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public String getTeacherId() {
		return teacherId;
	}
	
    
    // Setter
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
    
    
    // to.String()
	
    @Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", credits=" + credits + ", teacherId="
				+ teacherId + "]";
	}
    
    
    
}
