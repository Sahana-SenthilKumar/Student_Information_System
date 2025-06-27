package com.hexaware.sis.dao;

import java.util.List;

import com.hexaware.sis.entity.Course;
import com.hexaware.sis.entity.Teacher;

import com.hexaware.sis.exception.InvalidTeacherDataException;
import com.hexaware.sis.exception.TeacherNotFoundException;

public interface ITeacherDAO {
	
    public boolean insertTeacher(Teacher teacher) throws InvalidTeacherDataException ;
    
    public boolean updateTeacher(Teacher teacher) throws TeacherNotFoundException, InvalidTeacherDataException ;
    
    public boolean deleteTeacher(String teacherId) throws TeacherNotFoundException;
    
    public Teacher getTeacherById(String teacherId) throws TeacherNotFoundException;
    
    public List<Teacher> getAllTeachers();
    
    public boolean updateTeacherInfo(String teacherId, String firstName, String lastName, String email)
            throws TeacherNotFoundException, InvalidTeacherDataException;

    
    public boolean isTeacherExists(String teacherId);
    
    public List<Course> getAssignedCourses(String teacherId) throws TeacherNotFoundException;


}
