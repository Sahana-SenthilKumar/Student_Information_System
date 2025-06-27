package com.hexaware.sis.dao;

import java.util.List;

import com.hexaware.sis.entity.Payment;

import com.hexaware.sis.exception.PaymentNotFoundException;
import com.hexaware.sis.exception.PaymentValidationException;
import com.hexaware.sis.exception.StudentNotFoundException;

public interface IPaymentDAO {
	
    public boolean insertPayment(Payment payment) throws PaymentValidationException, StudentNotFoundException;

    public Payment getPaymentById(int paymentId) throws PaymentNotFoundException;

    public List<Payment> getAllPayments();

    public List<Payment> getPaymentsByStudentId(String studentId) throws StudentNotFoundException;
    
    

}
