package com.excilys.cdb.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class DtoComputer {
	
	@Min(1)
	private Integer id;
	@NonNull
	@NotEmpty
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	@Nullable
	@Min(1)
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

	@Override
	public String toString() {
		return "DtoComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
	}
	
}
