package com.hexaware.sis.exception;

public class PaymentValidationException extends Exception{
	
	public PaymentValidationException(String msg) {
		super(msg);
	}
	
	public PaymentValidationException() {
		super("Payment Validation Exception.\n");
	}

}
