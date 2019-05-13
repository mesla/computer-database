package com.excilys.cdb.model;

import java.sql.Timestamp;

public class ModelComputer{
	private Integer id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private ModelCompany modelCompany;
	
	public ModelComputer(Integer pId, String pName, Timestamp pIntroduced, Timestamp pDiscontinued, ModelCompany modelCompany) {
		this.id = pId;
		this.name = pName;
		this.introduced = pIntroduced;
		this.discontinued = pDiscontinued;
		this.modelCompany = modelCompany;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Timestamp getIntroduced() {
		return introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public Integer getCompanyId() {
		return modelCompany==null ? null : modelCompany.getId();
	}

	public String getCompanyName() {
		return modelCompany==null ? null : modelCompany.getName();
	}

}
