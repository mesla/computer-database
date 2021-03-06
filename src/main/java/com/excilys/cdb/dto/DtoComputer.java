package com.excilys.cdb.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

public class DtoComputer {
	
	@Nullable
	@Min(1)
	private Long id;
	@NotEmpty
	private String name;
	@DateTimeFormat(pattern= "yyyy-mm-dd")
	private LocalDate introduced;
	@DateTimeFormat(pattern= "yyyy-mm-dd")
	private LocalDate discontinued;
	@Nullable
	@Min(1)
	private Long companyId;
	@Nullable
	private String companyName;
	
	public DtoComputer(Long pId, String pName, LocalDate pIntroduced, LocalDate pDiscontinued, Long pCompanyId, String pCompanyName) {
		this.id =  pId;
		this.name = pName;
		this.introduced = pIntroduced;
		this.discontinued = pDiscontinued;
		this.companyId = pCompanyId;
		this.companyName = pCompanyName;
	}
	
	public Long getId() {
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

	public Long getCompanyId() {
		return companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	@Override
	public String toString() {
		return "DtoComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
	}
	
}
