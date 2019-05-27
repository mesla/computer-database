package com.excilys.cdb.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

public class DtoCompany {
	@Nullable
	@Min(1)
	private Integer id;
	@NotEmpty
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
