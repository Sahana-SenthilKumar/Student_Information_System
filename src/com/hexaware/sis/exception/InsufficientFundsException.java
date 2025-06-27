package com.hexaware.sis.exception;

public class InsufficientFundsException extends Exception{
	
	public InsufficientFundsException(String msg) {
		super(msg);
	}
	
	public InsufficientFundsException() {
		super("Insufficient Funds.\n");
	}

}
