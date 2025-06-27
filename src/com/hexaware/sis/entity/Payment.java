package com.hexaware.sis.entity;

import java.sql.Date;
//import java.time.LocalDate;


public class Payment {

	// Field
	
    private int paymentId;
    private String studentId;
    private double amount;
    private Date paymentDate;

	
    // Constructor
    
	public Payment(int paymentId, String studentId, double amount, Date paymentDate) {
		super();
		this.paymentId = paymentId;
		this.studentId = studentId;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}
	
	public Payment( String studentId, double amount, Date paymentDate) {
		super();
		this.studentId = studentId;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}

	public Payment() {
		
		
	}
    // Getter
	
	public int getPaymentId() {
		return paymentId;
	}

	public String getStudentId() {
		return studentId;
	}
	
	public double getAmount() {
		return amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}
    
    
    // Setter
	
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
    
    
    // to.String()
    
	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", studentId=" + studentId + ", amount=" + amount + ", paymentDate="
				+ paymentDate + "]";
	}
	
	
	
}
