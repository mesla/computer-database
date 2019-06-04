package com.excilys.cdb.core.exception;

public class BadArgumentException extends Type404Exception {
	private static final long serialVersionUID = 1L;
	
	public BadArgumentException(String str) {
		super(str);
	}
}