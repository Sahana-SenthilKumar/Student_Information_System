package com.hexaware.sis.exception;

public class PaymentNotFoundException extends Exception {
    
	public PaymentNotFoundException(String message) {
        super(message);
    }
	
	public PaymentNotFoundException() {
		super("Payment Not Found.\n");
	}
	
}

