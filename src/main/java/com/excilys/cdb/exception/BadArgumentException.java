package com.excilys.cdb.exception;

public class BadArgumentException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BadArgumentException(String str) {
		super(str);
	}
}

