package com.excilys.cdb.dto;

public class DtoCompany {
	private Integer id;
	private String name;
	
	public DtoCompany(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}
