package com.prospecta.Exception;

public class InvalidProductDetailsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidProductDetailsException(String msg) {
		super(msg);
	}
}
