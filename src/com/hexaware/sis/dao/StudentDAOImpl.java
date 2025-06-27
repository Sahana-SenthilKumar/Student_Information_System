package com.hexaware.sis.dao;

import java.sql.*;
import java.util.*;

import com.hexaware.sis.entity.*;
import com.hexaware.sis.exception.CourseNotFoundException;
import com.hexaware.sis.exception.DuplicateEnrollmentException;
import com.hexaware.sis.exception.InvalidEnrollmentDataException;
import com.hexaware.sis.exception.InvalidStudentDataException;
import com.hexaware.sis.exception.StudentNotFoundException;
import com.hexaware.sis.util.DBConnUtil;

public class StudentDAOImpl implements IStudentDAO {

    private ICourseDAO courseDao = new CourseDAOImpl();
    private IEnrollmentDAO enrollmentDao;

    public StudentDAOImpl() {}

    public StudentDAOImpl(IEnrollmentDAO enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }

    public void setEnrollmentDao(IEnrollmentDAO enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }

    // ---------------------- INSERT ----------------------
    @Override
    public boolean insertStudent(Student student) throws InvalidStudentDataException {
        String query = "INSERT INTO Students(student_id, first_name, last_name, date_of_birth, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setDate(4, student.getDateOfBirth());
            pstmt.setString(5, student.getEmail());
            pstmt.setLong(6, student.getPhoneNumber());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // ---------------------- UPDATE ----------------------
    @Override
    public boolean updateStudent(Student student) throws StudentNotFoundException, InvalidStudentDataException {
        String query = "UPDATE Students SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone_number = ? WHERE student_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setDate(3, student.getDateOfBirth());
            pstmt.setString(4, student.getEmail());
            pstmt.setLong(5, student.getPhoneNumber());
            pstmt.setString(6, student.getStudentId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // ---------------------- DELETE ----------------------
    @Override
    public boolean deleteStudent(String studentId) throws StudentNotFoundException {
        String query = "DELETE FROM Students WHERE student_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // ---------------------- GET BY ID ----------------------
    @Override
    public Student getStudentById(String studentId) throws StudentNotFoundException {
        String query = "SELECT * FROM Students WHERE student_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Student student = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new Student(
                    rs.getString("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("date_of_birth"),
                    rs.getString("email"),
                    rs.getLong("phone_number")
                );
            }
        } catch (SQLException e) {
            student = null;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return student;
    }

    // ---------------------- GET ALL ----------------------
    @Override
    public List<Student> getAllStudents() {
        String query = "SELECT * FROM Students";
        Statement stmt = null;
        ResultSet rs = null;
        List<Student> list = new ArrayList<>();

        try {
            Connection conn = DBConnUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Student s = new Student(
                    rs.getString("student_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("date_of_birth"),
                    rs.getString("email"),
                    rs.getLong("phone_number")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            return list;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closeStatement(stmt);
        }

        return list;
    }

    // ---------------------- UPDATE INFO ----------------------
    @Override
    public boolean updateStudentInfo(String studentId, String firstName, String lastName, java.sql.Date dob, String email, long phone) throws StudentNotFoundException, InvalidStudentDataException {
        String query = "UPDATE Students SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone_number = ? WHERE student_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, dob);
            pstmt.setString(4, email);
            pstmt.setLong(5, phone);
            pstmt.setString(6, studentId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // ---------------------- IS STUDENT EXISTS ----------------------
    @Override
    public boolean isStudentExists(String studentId) {
        String query = "SELECT 1 FROM Students WHERE student_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentId);
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

    // ---------------------- ENROLL IN COURSE ----------------------
    @Override
    public boolean enrollInCourse(String studentId, int courseId, java.sql.Date enrollmentDate) throws StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException, InvalidEnrollmentDataException {
        String query = "INSERT INTO Enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setDate(3, enrollmentDate);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }
    }

    // ---------------------- GET ENROLLED COURSES ----------------------
    @Override
    public List<Course> getEnrolledCourses(String studentId) throws StudentNotFoundException {
        String query = "SELECT c.course_id, c.course_name, c.credits, c.teacher_id " +
                       "FROM Courses c JOIN Enrollments e ON c.course_id = e.course_id " +
                       "WHERE e.student_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Course> list = new ArrayList<>();

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("credits"),
                    rs.getString("teacher_id")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            return list;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return list;
    }

    // ---------------------- GET PAYMENT HISTORY ----------------------
    @Override
    public List<Payment> getPaymentHistory(String studentId) throws StudentNotFoundException {
        String query = "SELECT * FROM Payments WHERE student_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Payment> list = new ArrayList<>();

        try {
            Connection conn = DBConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Payment p = new Payment(
                    rs.getInt("payment_id"),
                    rs.getString("student_id"),
                    rs.getDouble("amount"),
                    rs.getDate("payment_date")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            return list;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return list;
    }
}
