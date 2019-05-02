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
		return modelCompany.getId();
	}

	public String getCompanyName() {
		return modelCompany.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((modelCompany == null) ? 0 : modelCompany.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelComputer other = (ModelComputer) obj;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (modelCompany == null) {
			if (other.modelCompany != null)
				return false;
		} else if (!modelCompany.equals(other.modelCompany))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
		
}
