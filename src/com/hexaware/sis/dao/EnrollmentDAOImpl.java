package com.hexaware.sis.dao;

import java.sql.*;
import java.util.*;

import com.hexaware.sis.entity.Enrollment;
import com.hexaware.sis.exception.CourseNotFoundException;
import com.hexaware.sis.exception.DuplicateEnrollmentException;
import com.hexaware.sis.exception.EnrollmentNotFoundException;
import com.hexaware.sis.exception.InvalidEnrollmentDataException;
import com.hexaware.sis.exception.StudentNotFoundException;
import com.hexaware.sis.util.DBConnUtil;

public class EnrollmentDAOImpl implements IEnrollmentDAO {

    private IStudentDAO studentDao;
    private ICourseDAO courseDao = new CourseDAOImpl();

    public EnrollmentDAOImpl() {}

    public EnrollmentDAOImpl(IStudentDAO studentDao) {
        this.studentDao = studentDao;
    }

    public void setStudentDao(IStudentDAO studentDao) {
        this.studentDao = studentDao;
    }

    @Override // Insert Enrollment
    public boolean insertEnrollment(Enrollment enrollment) 
    		throws InvalidEnrollmentDataException, StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException {
        String query = "INSERT INTO Enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, enrollment.getStudentId());
            pstmt.setInt(2, enrollment.getCourseId());
            pstmt.setDate(3, enrollment.getEnrollmentDate());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    @Override // Delete Enrollment
    public boolean deleteEnrollment(int enrollmentId) throws EnrollmentNotFoundException {
        String query = "DELETE FROM Enrollments WHERE enrollment_id = ?";
        PreparedStatement pstmt = null;
        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, enrollmentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    @Override // Get by ID
    public Enrollment getEnrollmentById(int enrollmentId) throws EnrollmentNotFoundException {
        String query = "SELECT * FROM Enrollments WHERE enrollment_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Enrollment e = null;
        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, enrollmentId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                e = new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getString("student_id"),
                    rs.getInt("course_id"),
                    rs.getDate("enrollment_date")
                );
            }
        } catch (SQLException ex) {
            return null;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }
        return e;
    }

    @Override // Get All
    public List<Enrollment> getAllEnrollments() {
        String query = "SELECT * FROM Enrollments";
        Statement stmt = null;
        ResultSet rs = null;
        List<Enrollment> list = new ArrayList<>();
        try {
            Connection conn = DBConnUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                list.add(new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getString("student_id"),
                    rs.getInt("course_id"),
                    rs.getDate("enrollment_date")
                ));
            }
        } catch (SQLException e) {
            return list;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closeStatement(stmt);
        }
        return list;
    }

    @Override // Get by Student
    public List<Enrollment> getEnrollmentsByStudentId(String studentId) throws StudentNotFoundException {
        String query = "SELECT * FROM Enrollments WHERE student_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Enrollment> list = new ArrayList<>();
        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getString("student_id"),
                    rs.getInt("course_id"),
                    rs.getDate("enrollment_date")
                ));
            }
        } catch (SQLException e) {
            return list;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }
        return list;
    }

    @Override // Get by Course
    public List<Enrollment> getEnrollmentsByCourseId(int courseId)  throws CourseNotFoundException {
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
                list.add(new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getString("student_id"),
                    rs.getInt("course_id"),
                    rs.getDate("enrollment_date")
                ));
            }
        } catch (SQLException e) {
            return list;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }
        return list;
    }

    @Override // Already Enrolled?
    public boolean isAlreadyEnrolled(String studentId, int courseId) {
        String query = "SELECT 1 FROM Enrollments WHERE student_id = ? AND course_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentId);
            pstmt.setInt(2, courseId);
            rs = pstmt.executeQuery();
            exists = rs.next();
        } catch (SQLException e) {
            exists = false;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }
        return exists;
    }
}