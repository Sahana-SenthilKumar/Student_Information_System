package com.hexaware.sis.dao;

import java.util.List;

import com.hexaware.sis.entity.Enrollment;

import com.hexaware.sis.exception.CourseNotFoundException;
import com.hexaware.sis.exception.DuplicateEnrollmentException;
import com.hexaware.sis.exception.EnrollmentNotFoundException;
import com.hexaware.sis.exception.InvalidEnrollmentDataException;
import com.hexaware.sis.exception.StudentNotFoundException;

public interface IEnrollmentDAO {
	
    // Insert
    public boolean insertEnrollment(Enrollment enrollment) 
        throws InvalidEnrollmentDataException, StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException;

    // Delete
    public boolean deleteEnrollment(int enrollmentId) 
        throws EnrollmentNotFoundException;

    // Get by ID
    public Enrollment getEnrollmentById(int enrollmentId) 
        throws EnrollmentNotFoundException;

    // Get all
    public List<Enrollment> getAllEnrollments();

    // Get enrollments by student ID
    public List<Enrollment> getEnrollmentsByStudentId(String studentId) 
        throws StudentNotFoundException;

    // Get enrollments by course ID
    public List<Enrollment> getEnrollmentsByCourseId(int courseId) 
        throws CourseNotFoundException;

    // Check if already enrolled
    public boolean isAlreadyEnrolled(String studentId, int courseId);
    


}
