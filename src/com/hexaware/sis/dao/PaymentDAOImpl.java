package com.hexaware.sis.dao;

import java.sql.*;
import java.util.*;

import com.hexaware.sis.entity.Payment;
import com.hexaware.sis.exception.PaymentNotFoundException;
import com.hexaware.sis.exception.PaymentValidationException;
import com.hexaware.sis.exception.StudentNotFoundException;
import com.hexaware.sis.util.DBConnUtil;

public class PaymentDAOImpl implements IPaymentDAO {

    private IStudentDAO studentDao = new StudentDAOImpl();

    @Override
    public boolean insertPayment(Payment payment) throws PaymentValidationException, StudentNotFoundException {
        String query = "INSERT INTO Payments (student_id, amount, payment_date) VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;
        int rows = 0;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, payment.getStudentId());
            pstmt.setDouble(2, payment.getAmount());
            pstmt.setDate(3, payment.getPaymentDate());

            rows = pstmt.executeUpdate();

        } catch (SQLException e) {
            rows = 0;
        } finally {
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return rows > 0;
    }

    @Override
    public Payment getPaymentById(int paymentId)throws PaymentNotFoundException {
        String query = "SELECT * FROM Payments WHERE payment_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Payment payment = null;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, paymentId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                payment = new Payment(
                        rs.getInt("payment_id"),
                        rs.getString("student_id"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date")
                );
            }
        } catch (SQLException e) {
            payment = null;
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return payment;
    }

    @Override
    public List<Payment> getAllPayments() {
        String query = "SELECT * FROM Payments";
        List<Payment> list = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection connection = DBConnUtil.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("payment_id"),
                        rs.getString("student_id"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date")
                );
                list.add(payment);
            }

        } catch (SQLException ignored) {
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closeStatement(stmt);
        }

        return list;
    }

    @Override
    public List<Payment> getPaymentsByStudentId(String studentId) throws StudentNotFoundException {
        String query = "SELECT * FROM Payments WHERE student_id = ?";
        List<Payment> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection connection = DBConnUtil.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("payment_id"),
                        rs.getString("student_id"),
                        rs.getDouble("amount"),
                        rs.getDate("payment_date")
                );
                list.add(payment);
            }

        } catch (SQLException ignored) {
        } finally {
            DBConnUtil.closeResultSet(rs);
            DBConnUtil.closePreparedStatement(pstmt);
        }

        return list;
    }
}
