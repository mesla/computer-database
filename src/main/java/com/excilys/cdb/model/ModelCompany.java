package com.excilys.cdb.model;

public class ModelCompany {
	private Integer id;
	private String name;
	
	public ModelCompany(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
