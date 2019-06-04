package com.excilys.cdb.exception;

public abstract class Type404Exception extends RuntimeException {
	
	private static final long serialVersionUID = 5092724138003618395L;

	public Type404Exception(String str) {
		super(str);
	}

}
