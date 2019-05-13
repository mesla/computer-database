package com.excilys.cdb.dto;

import java.time.LocalDate;

public class DtoComputer{
	
	private Integer id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Integer companyId;
	private String companyName;
	
	public DtoComputer(Integer pId, String pName, LocalDate pIntroduced, LocalDate pDiscontinued, Integer pCompanyId, String pCompanyName) {
		this.id =  pId;
		this.name = pName;
		this.introduced = pIntroduced;
		this.discontinued = pDiscontinued;
		this.companyId = pCompanyId;
		this.companyName = pCompanyName;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}
}
