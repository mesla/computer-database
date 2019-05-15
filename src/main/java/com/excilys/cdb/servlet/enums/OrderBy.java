package com.excilys.cdb.servlet.enums;

public enum OrderBy {
	
	ORDERBY_COMPUTER_NAME_ASC("computer.name", "ASC"),
	ORDERBY_COMPUTER_NAME_DESC("computer.name", "DESC"),
	ORDERBY_INTRODUCED_ASC("computer.introduced", "ASC"),
	ORDERBY_INTRODUCED_DESC("computer.introduced", "DESC"),
	ORDERBY_DISCONTINUED_ASC("computer.discontinued", "ASC"),
	ORDERBY_DISCONTINUED_DESC("computer.discontinued", "DESC"),
	ORDERBY_COMPANY_NAME_ASC("company.name", "ASC"),
	ORDERBY_COMPANY_NAME_DESC("company.name", "DESC"),
	ORDERBY_COMPUTER_ID_ASC("computer.id", "ASC");
	
	private String field;
	private String direction;
	
	OrderBy(String field, String direction) {
		this.field = field;
		this.direction = direction;
	}
	
	public String getField() {
		return field;
	}
	
	public String getDirection() {
		return direction;
	}
		
}