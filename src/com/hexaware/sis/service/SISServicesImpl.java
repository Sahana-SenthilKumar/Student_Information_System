package com.hexaware.sis.service;

//import java.sql.Date;
//import java.time.LocalDate;
import java.util.*;

import com.hexaware.sis.dao.*;
import com.hexaware.sis.entity.*;
import com.hexaware.sis.exception.CourseNotFoundException;
import com.hexaware.sis.exception.DuplicateEnrollmentException;
import com.hexaware.sis.exception.EnrollmentNotFoundException;
import com.hexaware.sis.exception.InvalidCourseDataException;
import com.hexaware.sis.exception.InvalidEnrollmentDataException;
import com.hexaware.sis.exception.InvalidStudentDataException;
import com.hexaware.sis.exception.InvalidTeacherDataException;
import com.hexaware.sis.exception.PaymentNotFoundException;
import com.hexaware.sis.exception.PaymentValidationException;
import com.hexaware.sis.exception.StudentNotFoundException;
import com.hexaware.sis.exception.TeacherNotFoundException;

public class SISServicesImpl implements ISISService {
	
	private StudentDAOImpl studentDao;
	private EnrollmentDAOImpl enrollmentDao;

    private ITeacherDAO teacherDao = new TeacherDAOImpl();
    private ICourseDAO courseDao = new CourseDAOImpl();
    private IPaymentDAO paymentDao = new PaymentDAOImpl();
    
    
    public SISServicesImpl() {
        this.studentDao = new StudentDAOImpl();
        this.enrollmentDao = new EnrollmentDAOImpl();

        this.studentDao.setEnrollmentDao(enrollmentDao);
        this.enrollmentDao.setStudentDao(studentDao);

        this.teacherDao = new TeacherDAOImpl();
        this.courseDao = new CourseDAOImpl();
    }

 // -----------------------------------------------------------------------------------------------------------------------------------------------------
    
 // -- SIS OWN METHODS --

    @Override // 1. Enroll a Student in a Course
    public boolean enrollStudentInCourse(Student student, Course course)
            throws InvalidEnrollmentDataException, StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException {

        if (student == null || course == null) {
            throw new InvalidEnrollmentDataException("Student or Course cannot be null.");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(student.getStudentId());
        enrollment.setCourseId(course.getCourseId());
        enrollment.setEnrollmentDate(new java.sql.Date(System.currentTimeMillis()));

        return enrollmentDao.insertEnrollment(enrollment);
    }

    @Override // 2. Assign Teacher to a Course
    public boolean assignTeacherToCourse(Teacher teacher, Course course)
            throws TeacherNotFoundException, CourseNotFoundException {

        if (teacher == null || course == null) {
            return false;
        }

        return courseDao.assignTeacherToCourse(course.getCourseId(), teacher.getTeacherId());
    }

    @Override // 3. Record Payment for a Student
    public boolean recordPayment(Student student, double amount, java.sql.Date paymentDate)
            throws StudentNotFoundException, PaymentValidationException {

        if (student == null || amount <= 0 || paymentDate == null) {
            throw new PaymentValidationException("Invalid payment data.");
        }

        Payment payment = new Payment();
        payment.setStudentId(student.getStudentId());
        payment.setAmount(amount);
        payment.setPaymentDate(paymentDate);

        return paymentDao.insertPayment(payment);
    }

 // 4. Generate Enrollment Report for a Course
    @Override
    public List<Enrollment> generateEnrollmentReport(Course course) throws CourseNotFoundException {
        if (course == null || course.getCourseId() <= 0) {
            throw new CourseNotFoundException("Invalid course provided.");
        }
        return enrollmentDao.getEnrollmentsByCourseId(course.getCourseId());
    }

    // 5. Generate Payment Report for a Student
    @Override
    public List<Payment> generatePaymentReport(Student student) throws StudentNotFoundException {
        if (student == null || student.getStudentId() == null || student.getStudentId().isEmpty()) {
            throw new StudentNotFoundException("Invalid student provided.");
        }
        return paymentDao.getPaymentsByStudentId(student.getStudentId());
    }

    // 6. Calculate Course Statistics
    @Override
    public int calculateCourseStatistics(Course course) throws CourseNotFoundException {
        if (course == null || course.getCourseId() <= 0) {
            throw new CourseNotFoundException("Invalid course provided.");
        }
        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByCourseId(course.getCourseId());
        return enrollments.size(); // return total enrollments
    }



    
    

 // -----------------------------------------------------------------------------------------------------------------------------------------------------
   
    // - STUDENT METHOD --
    
    @Override // Insert
    public boolean addStudent(Student student) throws InvalidStudentDataException {
        
    	if (student == null) {
            throw new InvalidStudentDataException("Student object is null.");
        }
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            throw new InvalidStudentDataException("Student ID is required.");
        }
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            throw new InvalidStudentDataException("First name is required.");
        }
        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            throw new InvalidStudentDataException("Last name is required.");
        }
        if (student.getDateOfBirth() == null) {
            throw new InvalidStudentDataException("Date of birth is required.");
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty() || !student.getEmail().contains("@")) {
            throw new InvalidStudentDataException("Valid email is required.");
        }
        if (student.getPhoneNumber() <= 0 || String.valueOf(student.getPhoneNumber()).length() != 10 ) {
            throw new InvalidStudentDataException("Phone number must be positive.");
        }

        if (studentDao.isStudentExists(student.getStudentId())) {
            throw new InvalidStudentDataException("Student already exists with ID: " + student.getStudentId());
        }

        return studentDao.insertStudent(student);
    }


    @Override // Update
    public boolean updateStudent(Student student) throws StudentNotFoundException, InvalidStudentDataException {
        
    	if (student == null) {
            throw new InvalidStudentDataException("Student object is null.");
        }

        if (!studentDao.isStudentExists(student.getStudentId())) {
            throw new StudentNotFoundException("Student not found with ID: " + student.getStudentId());
        }

        return studentDao.updateStudent(student);
    }


    @Override // Delete
    public boolean deleteStudent(String studentId) throws StudentNotFoundException {
        if (!studentDao.isStudentExists(studentId)) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }

        return studentDao.deleteStudent(studentId);
    }


    @Override // Get By ID
    public Student getStudentById(String studentId) throws StudentNotFoundException {
        Student s = studentDao.getStudentById(studentId);

        if (s == null) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }

        return s;
    }


    @Override // Get All
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }


    @Override // Update Info by fields
    public boolean updateStudentInfo(String studentId, String firstName, String lastName, java.sql.Date dob, String email, long phone)
            throws StudentNotFoundException, InvalidStudentDataException {

        if (!studentDao.isStudentExists(studentId)) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidStudentDataException("First name is required.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidStudentDataException("Last name is required.");
        }
        if (dob == null) {
            throw new InvalidStudentDataException("Date of birth is required.");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new InvalidStudentDataException("Valid email is required.");
        }
        if (phone <= 0 || String.valueOf(phone).length() != 10 ) {
            throw new InvalidStudentDataException("Phone number invalid.");
        }

        return studentDao.updateStudentInfo(studentId, firstName, lastName, dob, email, phone);
    }


    @Override // Enroll Student in Course
    public boolean enrollInCourse(String studentId, int courseId, java.sql.Date enrollmentDate)
            throws StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException, InvalidEnrollmentDataException {

        if (enrollmentDate == null ) {
            throw new InvalidEnrollmentDataException("Invalid enrollment date.");
        }

        if (!studentDao.isStudentExists(studentId)) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }
        
        if (!courseDao.isCourseExists(courseId)) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
        
        if (enrollmentDao != null && enrollmentDao.isAlreadyEnrolled(studentId, courseId)) {
            throw new DuplicateEnrollmentException("Student is already enrolled in this course.");
        }

        return studentDao.enrollInCourse(studentId, courseId, enrollmentDate);
    }


    @Override // Get Enrolled Courses
    public List<Course> getEnrolledCourses(String studentId) throws StudentNotFoundException {
        if (!studentDao.isStudentExists(studentId)) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }

        return studentDao.getEnrolledCourses(studentId);
    }


    @Override // Get Payment History
    public List<Payment> getPaymentHistory(String studentId) throws StudentNotFoundException {
        if (!studentDao.isStudentExists(studentId)) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }

        return studentDao.getPaymentHistory(studentId);
    }
    
 
//----------------------------------------------------------------------------------------------------------------------------------------
	
    
    // -- TEACHER METHODS --
    
    // -- Insert Teacher --
    @Override
    public boolean addTeacher(Teacher teacher) throws InvalidTeacherDataException {
        if (teacher == null || teacher.getTeacherId() == null || teacher.getTeacherId().isEmpty()
            || teacher.getFirstName() == null || teacher.getFirstName().isEmpty()
            || teacher.getLastName() == null || teacher.getLastName().isEmpty()
            || teacher.getEmail() == null || teacher.getEmail().isEmpty() || !teacher.getEmail().contains("@")) {

            throw new InvalidTeacherDataException("Teacher data is invalid or missing.");
        }

        if (teacherDao.isTeacherExists(teacher.getTeacherId())) {
            throw new InvalidTeacherDataException("Teacher ID already exists.");
        }

        return teacherDao.insertTeacher(teacher);
    }


    // -- Update Teacher --
    @Override
    public boolean updateTeacher(Teacher teacher) throws TeacherNotFoundException, InvalidTeacherDataException {
    	
    	if (teacher == null) {
    	    throw new InvalidTeacherDataException("Teacher object is null.");
    	}
    	if (teacher.getTeacherId() == null || teacher.getTeacherId().isEmpty()) {
    	    throw new InvalidTeacherDataException("Teacher ID is required and cannot be empty.");
    	}
    	if (teacher.getFirstName() == null || teacher.getFirstName().isEmpty()) {
    	    throw new InvalidTeacherDataException("First name is required and cannot be empty.");
    	}
    	if (teacher.getLastName() == null || teacher.getLastName().isEmpty()) {
    	    throw new InvalidTeacherDataException("Last name is required and cannot be empty.");
    	}
    	if (teacher.getEmail() == null || teacher.getEmail().isEmpty()) {
    	    throw new InvalidTeacherDataException("Email is required and cannot be empty.");
    	}
    	if (!teacher.getEmail().contains("@")) {
    	    throw new InvalidTeacherDataException("Invalid email format. '@' symbol missing.");
    	}
    	
        if (!teacherDao.isTeacherExists(teacher.getTeacherId())) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacher.getTeacherId());
        }

        return teacherDao.updateTeacher(teacher);
    }


    // -- Update Teacher Info --
    @Override
    public boolean updateTeacherInfo(String teacherId, String firstName, String lastName, String email)
            throws TeacherNotFoundException, InvalidTeacherDataException {
    	
    	if (teacherId == null || teacherId.isEmpty()) {
    	    throw new InvalidTeacherDataException("Teacher ID is required and cannot be empty.");
    	}
    	if (firstName == null || firstName.isEmpty()) {
    	    throw new InvalidTeacherDataException("First name is required and cannot be empty.");
    	}
    	if (lastName == null || lastName.isEmpty()) {
    	    throw new InvalidTeacherDataException("Last name is required and cannot be empty.");
    	}
    	if (email == null || email.isEmpty()) {
    	    throw new InvalidTeacherDataException("Email is required and cannot be empty.");
    	}
    	if (!email.contains("@")) {
    	    throw new InvalidTeacherDataException("Invalid email format. '@' symbol missing.");
    	}

        if (!teacherDao.isTeacherExists(teacherId)) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacherId);
        }

        return teacherDao.updateTeacherInfo(teacherId, firstName, lastName, email);
    }


    // -- Delete Teacher --
    @Override
    public boolean deleteTeacher(String teacherId) throws TeacherNotFoundException {
        if (!teacherDao.isTeacherExists(teacherId)) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacherId);
        }

        return teacherDao.deleteTeacher(teacherId);
    }


    // -- Get Teacher By ID --
    @Override
    public Teacher getTeacherById(String teacherId) throws TeacherNotFoundException {
        Teacher teacher = teacherDao.getTeacherById(teacherId);
        if (teacher == null) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacherId);
        }
        return teacher;
    }


    // -- Get All Teachers --
    @Override
    public List<Teacher> getAllTeachers() {
        return teacherDao.getAllTeachers();
    }


    // -- Get Assigned Courses --
    @Override
    public List<Course> getAssignedCourses(String teacherId) throws TeacherNotFoundException {
        if (!teacherDao.isTeacherExists(teacherId)) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacherId);
        }

        return teacherDao.getAssignedCourses(teacherId);
    }
	
// -----------------------------------------------------------------------------------------------------------------------------------------------------
	
	// -- COURSE --

    @Override // Insert
    public boolean addCourse(Course course) throws InvalidCourseDataException, TeacherNotFoundException {
        
    	if (course == null) {
            throw new InvalidCourseDataException("Course object cannot be null.");
        }
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new InvalidCourseDataException("Course name cannot be null or empty.");
        }
        if (course.getCredits() <= 0) {
            throw new InvalidCourseDataException("Course credits must be greater than zero.");
        }
        if (course.getTeacherId() == null || course.getTeacherId().trim().isEmpty()) {
            throw new InvalidCourseDataException("Teacher ID cannot be null or empty.");
        }

        boolean teacherExists = teacherDao.isTeacherExists(course.getTeacherId());
        if (!teacherExists) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + course.getTeacherId());
        }

        return courseDao.insertCourse(course);
    }

    @Override // Update
    public boolean updateCourse(Course course) throws CourseNotFoundException, InvalidCourseDataException, TeacherNotFoundException {
       
    	if (course == null) {
            throw new InvalidCourseDataException("Course object cannot be null.");
        }
        if (course.getCourseId() <= 0) {
            throw new InvalidCourseDataException("Invalid course ID.");
        }
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new InvalidCourseDataException("Course name cannot be null or empty.");
        }
        if (course.getCredits() <= 0) {
            throw new InvalidCourseDataException("Credits must be positive.");
        }
        if (course.getTeacherId() == null || course.getTeacherId().trim().isEmpty()) {
            throw new InvalidCourseDataException("Teacher ID is required.");
        }

        boolean courseExists = courseDao.isCourseExists(course.getCourseId());
        if (!courseExists) {
            throw new CourseNotFoundException("Course not found with ID: " + course.getCourseId());
        }

        boolean teacherExists = teacherDao.isTeacherExists(course.getTeacherId());
        if (!teacherExists) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + course.getTeacherId());
        }

        return courseDao.updateCourse(course);
    }

    @Override // Delete
    public boolean deleteCourse(int courseId) throws CourseNotFoundException {
        boolean courseExists = courseDao.isCourseExists(courseId);
        if (!courseExists) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }

        return courseDao.deleteCourse(courseId);
    }

    @Override // Assign Teacher to Course
    public boolean assignTeacherToCourse(int courseId, String teacherId) throws CourseNotFoundException, TeacherNotFoundException {
        if (courseId <= 0) {
            throw new CourseNotFoundException("Invalid course ID.");
        }

        if (teacherId == null || teacherId.trim().isEmpty()) {
            throw new TeacherNotFoundException("Teacher ID cannot be null or empty.");
        }

        boolean courseExists = courseDao.isCourseExists(courseId);
        if (!courseExists) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }

        boolean teacherExists = teacherDao.isTeacherExists(teacherId);
        if (!teacherExists) {
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacherId);
        }

        return courseDao.assignTeacherToCourse(courseId, teacherId);
    }

    @Override // Update Course Info (name + credits only)
    public boolean updateCourseInfo(int courseId, String courseName, int credits)
            throws CourseNotFoundException, InvalidCourseDataException {

        if (courseId <= 0) {
            throw new CourseNotFoundException("Invalid course ID.");
        }
        
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new InvalidCourseDataException("Course name cannot be empty.");
        }
        if (credits <= 0) {
            throw new InvalidCourseDataException("Credits must be greater than zero.");
        }

        boolean courseExists = courseDao.isCourseExists(courseId);
        if (!courseExists) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }

        return courseDao.updateCourseInfo(courseId, courseName, credits);
    }

    @Override // Get Course by ID
    public Course getCourseById(int courseId) throws CourseNotFoundException {
        Course course = courseDao.getCourseById(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
        return course;
    }

    @Override // Get All Courses
    public List<Course> getAllCourses() {
        return courseDao.getAllCourses();
    }

    @Override // Get Enrollments by Course
    public List<Enrollment>  getEnrollmentsByCourseViaCourse(int courseId) throws CourseNotFoundException {
        Course course = courseDao.getCourseById(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }

        return courseDao.getEnrollmentsByCourse(courseId);
    }

    @Override // Get Assigned Teacher
    public Teacher getTeacherByCourse(int courseId) throws CourseNotFoundException {
        Course course = courseDao.getCourseById(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }

        return courseDao.getTeacherByCourseId(courseId);
    }

	
	
	
	
// -----------------------------------------------------------------------------------------------------------------------------------------------------
	
 // -- ENROLLMENT --

    @Override // Add Enrollment
    public boolean addEnrollment(Enrollment enrollment)
            throws InvalidEnrollmentDataException, StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException {

        if (enrollment == null || enrollment.getEnrollmentDate() == null) {
            throw new InvalidEnrollmentDataException("Enrollment data is invalid.");
        }

        if (!studentDao.isStudentExists(enrollment.getStudentId())) {
            throw new StudentNotFoundException("Student not found with ID: " + enrollment.getStudentId());
        }

        if (!courseDao.isCourseExists(enrollment.getCourseId())) {
            throw new CourseNotFoundException("Course not found with ID: " + enrollment.getCourseId());
        }

        if (enrollmentDao.isAlreadyEnrolled(enrollment.getStudentId(), enrollment.getCourseId())) {
            throw new DuplicateEnrollmentException("Already enrolled.");
        }

        boolean result = enrollmentDao.insertEnrollment(enrollment);
        if (result) {
            System.out.println("Enrollment inserted successfully.\n");
        } else {
            System.out.println("Enrollment insertion failed.\n");
        }

        return result;
    }

    @Override // Remove Enrollment
    public boolean removeEnrollment(int enrollmentId) throws EnrollmentNotFoundException {
        boolean result = enrollmentDao.deleteEnrollment(enrollmentId);
        if (result) {
            System.out.println("Enrollment deleted successfully.\n");
        } else {
            System.out.println("Enrollment deletion failed.\n");
        }
        return result;
    }


    @Override // Get by ID
    public Enrollment getEnrollmentById(int enrollmentId) throws EnrollmentNotFoundException {
        Enrollment e = enrollmentDao.getEnrollmentById(enrollmentId);
        if (e == null) {
            throw new EnrollmentNotFoundException("Enrollment ID not found.");
        }
        return e;
    }

    @Override // Get All
    public List<Enrollment> getAllEnrollments() {
        return enrollmentDao.getAllEnrollments();
    }

    @Override // Get by Student
    public List<Enrollment> getEnrollmentsByStudent(String studentId) throws StudentNotFoundException {
        if (!studentDao.isStudentExists(studentId)) {
            throw new StudentNotFoundException("Student ID not found.");
        }
        return enrollmentDao.getEnrollmentsByStudentId(studentId);
    }

    @Override // Get by Course
    public List<Enrollment> getEnrollmentsByCourseViaEnrollment(int courseId) throws CourseNotFoundException {
        if (!courseDao.isCourseExists(courseId)) {
            throw new CourseNotFoundException("Course ID not found.");
        }
        return enrollmentDao.getEnrollmentsByCourseId(courseId);
    }

	
	
	
// -----------------------------------------------------------------------------------------------------------------------------------------------------
		
 // -- PAYMENT METHODS --

    @Override
    public boolean addPayment(Payment payment) throws PaymentValidationException, StudentNotFoundException {
       
    	if (payment == null) {
            throw new PaymentValidationException("Payment object is null.");
        }
        if (payment.getStudentId() == null || payment.getStudentId().isEmpty()) {
            throw new PaymentValidationException("Student ID is required.");
        }
        if (payment.getAmount() <= 0) {
            throw new PaymentValidationException("Amount must be greater than zero.");
        }
        if (payment.getPaymentDate() == null) {
            throw new PaymentValidationException("Payment date is required.");
        }

        if (!studentDao.isStudentExists(payment.getStudentId())) {
            throw new StudentNotFoundException("Student not found with ID: " + payment.getStudentId());
        }

        return paymentDao.insertPayment(payment);
    }

    @Override
    public Payment getPaymentById(int paymentId) throws PaymentNotFoundException {
        Payment p = paymentDao.getPaymentById(paymentId);
        if (p == null) {
            throw new PaymentNotFoundException("Payment not found with ID: " + paymentId);
        }
        return p;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentDao.getAllPayments();
    }

    @Override
    public List<Payment> getPaymentsByStudent(String studentId) throws StudentNotFoundException {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new StudentNotFoundException("Student ID is required.");
        }

        if (!studentDao.isStudentExists(studentId)) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }

        return paymentDao.getPaymentsByStudentId(studentId);
    }








	

	




	
	
}




    


