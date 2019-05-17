package com.excilys.cdb.exception;

public abstract class errorTypeException extends Exception {

	private static final long serialVersionUID = -7078837589953831900L;
	
	public errorTypeException(String str) {
		super(str);
	}

}
