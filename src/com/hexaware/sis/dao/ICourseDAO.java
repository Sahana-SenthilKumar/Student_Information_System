package com.hexaware.sis.dao;

import java.util.List;

import com.hexaware.sis.entity.Course;
import com.hexaware.sis.entity.Enrollment;
import com.hexaware.sis.entity.Teacher;

import com.hexaware.sis.exception.CourseNotFoundException;
import com.hexaware.sis.exception.InvalidCourseDataException;
import com.hexaware.sis.exception.TeacherNotFoundException;

public interface ICourseDAO {
	
    public boolean insertCourse(Course course) throws InvalidCourseDataException, TeacherNotFoundException;

    public boolean updateCourse(Course course) throws CourseNotFoundException, InvalidCourseDataException, TeacherNotFoundException;

    public boolean deleteCourse(int courseId) throws CourseNotFoundException;

    public Course getCourseById(int courseId) throws CourseNotFoundException;

    public List<Course> getAllCourses();
    
    boolean isCourseExists(int courseId);
    
    // Assign a teacher to a course
    public boolean assignTeacherToCourse(int courseId, String teacherId) throws CourseNotFoundException, TeacherNotFoundException;

    // Update course info separately
    public boolean updateCourseInfo(int courseId, String courseName, int credits) throws CourseNotFoundException, InvalidCourseDataException;

    // Get enrollments for a course
    public List<Enrollment> getEnrollmentsByCourse(int courseId) throws CourseNotFoundException;

    // Get teacher assigned to course
    public Teacher getTeacherByCourseId(int courseId) throws CourseNotFoundException;


}
