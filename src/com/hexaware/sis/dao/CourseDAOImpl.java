package com.hexaware.sis.dao;

import java.sql.*;
import java.util.*;

import com.hexaware.sis.entity.*;
import com.hexaware.sis.exception.CourseNotFoundException;
import com.hexaware.sis.exception.InvalidCourseDataException;
import com.hexaware.sis.exception.TeacherNotFoundException;
import com.hexaware.sis.util.DBConnUtil;

public class CourseDAOImpl implements ICourseDAO {

    private ITeacherDAO teacherDao = new TeacherDAOImpl();

    // -------------------- INSERT --------------------
    @Override
    public boolean insertCourse(Course course) throws InvalidCourseDataException, TeacherNotFoundException {
        String query = "INSERT INTO Courses (course_name, credits, teacher_id) VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, course.getCourseName());
            pstmt.setInt(2, course.getCredits());
            pstmt.setString(3, course.getTeacherId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // -------------------- UPDATE --------------------
    @Override
    public boolean updateCourse(Course course) throws CourseNotFoundException, InvalidCourseDataException, TeacherNotFoundException {
        String query = "UPDATE Courses SET course_name = ?, credits = ?, teacher_id = ? WHERE course_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, course.getCourseName());
            pstmt.setInt(2, course.getCredits());
            pstmt.setString(3, course.getTeacherId());
            pstmt.setInt(4, course.getCourseId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // -------------------- DELETE --------------------
    @Override
    public boolean deleteCourse(int courseId) throws CourseNotFoundException {
        String query = "DELETE FROM Courses WHERE course_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // -------------------- GET BY ID --------------------
    @Override
    public Course getCourseById(int courseId) throws CourseNotFoundException {
        String query = "SELECT * FROM Courses WHERE course_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Course course = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getString("teacher_id")
                );
            }

        } catch (SQLException e) {
            return null;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return course;
    }

    // -------------------- GET ALL --------------------
    @Override
    public List<Course> getAllCourses() {
        String query = "SELECT * FROM Courses";
        Statement stmt = null;
        ResultSet rs = null;
        List<Course> list = new ArrayList<>();

        try {
            Connection conn = DBConnUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getString("teacher_id")
                );
                list.add(course);
            }

        } catch (SQLException e) {
            return list;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closeStatement(stmt);
        }

        return list;
    }

    // -------------------- EXISTS --------------------
    @Override
    public boolean isCourseExists(int courseId) {
        String query = "SELECT 1 FROM Courses WHERE course_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            rs = pstmt.executeQuery();
            exists = rs.next();

        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return exists;
    }

    // -------------------- ASSIGN TEACHER --------------------
    @Override
    public boolean assignTeacherToCourse(int courseId, String teacherId) throws CourseNotFoundException, TeacherNotFoundException {
        String query = "UPDATE Courses SET teacher_id = ? WHERE course_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, teacherId);
            pstmt.setInt(2, courseId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // -------------------- UPDATE COURSE INFO --------------------
    @Override
    public boolean updateCourseInfo(int courseId, String courseName, int credits) throws CourseNotFoundException, InvalidCourseDataException {
        String query = "UPDATE Courses SET course_name = ?, credits = ? WHERE course_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, courseName);
            pstmt.setInt(2, credits);
            pstmt.setInt(3, courseId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // -------------------- ENROLLMENTS BY COURSE --------------------
    @Override
    public List<Enrollment> getEnrollmentsByCourse(int courseId) throws CourseNotFoundException {
        String query = "SELECT * FROM Enrollments WHERE course_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Enrollment> list = new ArrayList<>();

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Enrollment e = new Enrollment(
                        rs.getInt("enrollment_id"),
                        rs.getString("student_id"),
                        rs.getInt("course_id"),
                        rs.getDate("enrollment_date")
                );
                list.add(e);
            }

        } catch (SQLException e) {
            return list;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return list;
    }

    // -------------------- TEACHER BY COURSE --------------------
    @Override
    public Teacher getTeacherByCourseId(int courseId)  throws CourseNotFoundException {
        String query = "SELECT t.teacher_id, t.first_name, t.last_name, t.email " +
                "FROM Teacher t JOIN Courses c ON t.teacher_id = c.teacher_id " +
                "WHERE c.course_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Teacher teacher = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                teacher = new Teacher(
                        rs.getString("teacher_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            return null;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return teacher;
    }
}
