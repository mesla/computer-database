package com.excilys.cdb.binding.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

public class DtoCompany {
	@Nullable
	@Min(1)
	private Long id;
	@NotEmpty
	private String name;
	
	public DtoCompany(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}
