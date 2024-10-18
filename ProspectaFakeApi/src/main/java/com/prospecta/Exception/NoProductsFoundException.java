package com.prospecta.Exception;

public class NoProductsFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NoProductsFoundException(String str) {
		super(str);
	}

}
