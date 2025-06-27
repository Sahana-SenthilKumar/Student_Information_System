package com.hexaware.sis.dao;

import java.util.*;

import com.hexaware.sis.entity.Course;
import com.hexaware.sis.entity.Payment;
import com.hexaware.sis.entity.Student;

import com.hexaware.sis.exception.CourseNotFoundException;
import com.hexaware.sis.exception.DuplicateEnrollmentException;
import com.hexaware.sis.exception.InvalidEnrollmentDataException;
import com.hexaware.sis.exception.InvalidStudentDataException;
import com.hexaware.sis.exception.StudentNotFoundException;

public interface IStudentDAO {
	
	public boolean insertStudent(Student student) throws InvalidStudentDataException;
	
    public boolean updateStudent(Student student) throws StudentNotFoundException, InvalidStudentDataException;
    
    public boolean deleteStudent(String studentId) throws StudentNotFoundException;
    
    public Student getStudentById(String studentId) throws StudentNotFoundException;
    
    public List<Student> getAllStudents();
    
    public boolean updateStudentInfo(String studentId, String firstName, String lastName, java.sql.Date dob, String email, long phone) throws StudentNotFoundException, InvalidStudentDataException;
    
    public boolean isStudentExists(String studentId);
    
    public boolean enrollInCourse(String studentId, int courseId, java.sql.Date enrollmentDate) throws StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException, InvalidEnrollmentDataException  ;
    
    public List<Course> getEnrolledCourses(String studentId) throws StudentNotFoundException;
    
    public List<Payment> getPaymentHistory(String studentId) throws StudentNotFoundException;


}
