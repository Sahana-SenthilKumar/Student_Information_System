package com.hexaware.sis.service;


import java.sql.Date;
import java.util.List;

import com.hexaware.sis.entity.*;
import com.hexaware.sis.exception.*;


public interface ISISService {
	

	    // -- SISServices methods --
	
	
	    boolean enrollStudentInCourse(Student student, Course course)
	        throws InvalidEnrollmentDataException, StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException;

	    boolean assignTeacherToCourse(Teacher teacher, Course course)
	        throws TeacherNotFoundException, CourseNotFoundException;

	    boolean recordPayment(Student student, double amount, Date paymentDate)
	        throws StudentNotFoundException, PaymentValidationException;

	    List<Enrollment> generateEnrollmentReport(Course course)
	        throws CourseNotFoundException;

	    List<Payment> generatePaymentReport(Student student)
	        throws StudentNotFoundException;

	    int calculateCourseStatistics(Course course)
	        throws CourseNotFoundException;
	    

// -----------------------------------------------------------------------------------------------------------------------------------------------------
    

	    // -- Student --
	    
	    
	    boolean addStudent(Student student) throws InvalidStudentDataException;

	    boolean updateStudent(Student student) throws StudentNotFoundException, InvalidStudentDataException;

	    boolean deleteStudent(String studentId) throws StudentNotFoundException;

	    Student getStudentById(String studentId) throws StudentNotFoundException;

	    List<Student> getAllStudents();

	    boolean updateStudentInfo(String studentId, String firstName, String lastName, Date dob, String email, long phone)
	        throws StudentNotFoundException, InvalidStudentDataException;

	    boolean enrollInCourse(String studentId, int courseId, Date enrollmentDate)
	        throws StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException, InvalidEnrollmentDataException;

	    List<Course> getEnrolledCourses(String studentId) throws StudentNotFoundException;

	    List<Payment> getPaymentHistory(String studentId) throws StudentNotFoundException;
	    

	    
// -----------------------------------------------------------------------------------------------------------------------------------------------------


	    // -- Teacher --
	    
	    boolean addTeacher(Teacher teacher) throws InvalidTeacherDataException;

	    boolean updateTeacher(Teacher teacher) throws InvalidTeacherDataException, TeacherNotFoundException;

	    boolean updateTeacherInfo(String teacherId, String firstName, String lastName, String email)
	        throws TeacherNotFoundException, InvalidTeacherDataException;

	    boolean deleteTeacher(String teacherId) throws TeacherNotFoundException;

	    Teacher getTeacherById(String teacherId) throws TeacherNotFoundException;

	    List<Teacher> getAllTeachers();

	    List<Course> getAssignedCourses(String teacherId) throws TeacherNotFoundException;
	    
	    
	    
// -----------------------------------------------------------------------------------------------------------------------------------------------------

	    
	    // -- Course --
	    
	    boolean addCourse(Course course) throws InvalidCourseDataException, TeacherNotFoundException;

	    boolean updateCourse(Course course) throws CourseNotFoundException, InvalidCourseDataException, TeacherNotFoundException;

	    boolean deleteCourse(int courseId) throws CourseNotFoundException;

	    boolean assignTeacherToCourse(int courseId, String teacherId) throws CourseNotFoundException, TeacherNotFoundException;

	    boolean updateCourseInfo(int courseId, String courseName, int credits)
	        throws CourseNotFoundException, InvalidCourseDataException;

	    Course getCourseById(int courseId) throws CourseNotFoundException;

	    List<Course> getAllCourses();

	    List<Enrollment> getEnrollmentsByCourseViaCourse(int courseId) throws CourseNotFoundException;

	    Teacher getTeacherByCourse(int courseId) throws CourseNotFoundException;
	    
	    
	    
// -----------------------------------------------------------------------------------------------------------------------------------------------------


	    // -- Enrollment --
	    
	    boolean addEnrollment(Enrollment enrollment)
	        throws InvalidEnrollmentDataException, StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException;

	    boolean removeEnrollment(int enrollmentId) throws EnrollmentNotFoundException;

	    Enrollment getEnrollmentById(int enrollmentId) throws EnrollmentNotFoundException;

	    List<Enrollment> getAllEnrollments();

	    List<Enrollment> getEnrollmentsByStudent(String studentId) throws StudentNotFoundException;

	    List<Enrollment> getEnrollmentsByCourseViaEnrollment(int courseId) throws CourseNotFoundException;
	    
	    

// -----------------------------------------------------------------------------------------------------------------------------------------------------

	    
	    // -- Payment --
	    boolean addPayment(Payment payment)
	        throws PaymentValidationException, StudentNotFoundException;

	    Payment getPaymentById(int paymentId) throws PaymentNotFoundException;

	    List<Payment> getAllPayments();

	    List<Payment> getPaymentsByStudent(String studentId) throws StudentNotFoundException;
		 


}
