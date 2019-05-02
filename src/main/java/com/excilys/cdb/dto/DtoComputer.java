package com.excilys.cdb.dto;

import java.util.Optional;

public class DtoComputer{
	
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyId;
	private String companyName;
	
	public DtoComputer(String pId, String pName, String pIntroduced, String pDiscontinued, String pCompanyId, String pCompanyName) {
		this.id =  pId;
		this.name = pName;
		this.introduced = pIntroduced;
		this.discontinued = pDiscontinued;
		this.companyId = pCompanyId;
		this.companyName = pCompanyName;
	}
	

	@Override
	public String toString() {
		return "DtoComputer [id=" + id 
				+ ", name=" + name 
				+ ", introduced=" + introduced
				+ ", discontinued="	+ discontinued
				+ ", company_id=" + companyId
				+ ", company_name=" + companyName
				+"]";
	}



	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Optional<String> getIntroduced() {
		return Optional.ofNullable(introduced);
	}

	public Optional<String> getDiscontinued() {
		return Optional.ofNullable(discontinued);
	}

	public String getCompanyId() {
		return companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		DtoComputer other = (DtoComputer) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
