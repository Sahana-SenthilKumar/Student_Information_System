package com.hexaware.sis.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.sis.entity.Course;
import com.hexaware.sis.entity.Teacher;
import com.hexaware.sis.exception.InvalidTeacherDataException;
import com.hexaware.sis.exception.TeacherNotFoundException;
import com.hexaware.sis.util.DBConnUtil;

public class TeacherDAOImpl implements ITeacherDAO {

    // -- Insert Teacher --
    @Override
    public boolean insertTeacher(Teacher teacher) throws InvalidTeacherDataException {
        String query = "INSERT INTO Teacher (teacher_id, first_name, last_name, email) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);

            pstmt.setString(1, teacher.getTeacherId());
            pstmt.setString(2, teacher.getFirstName());
            pstmt.setString(3, teacher.getLastName());
            pstmt.setString(4, teacher.getEmail());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return false;
    }

    // -- Update Teacher --
    @Override
    public boolean updateTeacher(Teacher teacher) throws TeacherNotFoundException, InvalidTeacherDataException {
        String query = "UPDATE Teacher SET first_name = ?, last_name = ?, email = ? WHERE teacher_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);

            pstmt.setString(1, teacher.getFirstName());
            pstmt.setString(2, teacher.getLastName());
            pstmt.setString(3, teacher.getEmail());
            pstmt.setString(4, teacher.getTeacherId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return false;
    }

    // -- Delete Teacher --
    @Override
    public boolean deleteTeacher(String teacherId) throws TeacherNotFoundException {
        String query = "DELETE FROM Teacher WHERE teacher_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, teacherId);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return false;
    }

    // -- Get Teacher by ID --
    @Override
    public Teacher getTeacherById(String teacherId) throws TeacherNotFoundException {
        String query = "SELECT * FROM Teacher WHERE teacher_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Teacher teacher = null;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, teacherId);

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
            e.printStackTrace();
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return teacher;
    }

    // -- Get All Teachers --
    @Override
    public List<Teacher> getAllTeachers() {
        String query = "SELECT * FROM Teacher";
        Statement stmt = null;
        ResultSet rs = null;
        List<Teacher> list = new ArrayList<>();

        try {
            Connection connection = DBConnUtil.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Teacher t = new Teacher(
                    rs.getString("teacher_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email")
                );
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closeStatement(stmt);
        }

        return list;
    }

    // -- Update Teacher Info --
    @Override
    public boolean updateTeacherInfo(String teacherId, String firstName, String lastName, String email)
            throws TeacherNotFoundException, InvalidTeacherDataException {
        String query = "UPDATE Teacher SET first_name = ?, last_name = ?, email = ? WHERE teacher_id = ?";
        PreparedStatement pstmt = null;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, teacherId);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return false;
    }

    // -- Check Teacher Exists --
    @Override
    public boolean isTeacherExists(String teacherId) {
        String query = "SELECT 1 FROM Teacher WHERE teacher_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, teacherId);
            rs = pstmt.executeQuery();

            exists = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return exists;
    }

    // -- Get Assigned Courses --
    @Override
    public List<Course> getAssignedCourses(String teacherId) throws TeacherNotFoundException {
        String query = "SELECT * FROM Courses WHERE teacher_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, teacherId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                    rs.getInt("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("credits"),
                    rs.getString("teacher_id")
                );
                courses.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return courses;
    }
}
