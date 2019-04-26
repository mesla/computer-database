package com.excilys.cdb.dto;

public class DtoComputer{
	
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	DtoCompany dtocompany;
	
	public DtoComputer(String pId, String pName, String pIntroduced, String pDiscontinued, DtoCompany dtocompany) {
		this.id =  pId;
		this.name = pName;
		this.introduced = pIntroduced  == null ? "null" : pIntroduced ;
		this.discontinued = pDiscontinued == null ? "null" : pDiscontinued;
		this.dtocompany = dtocompany;
	}
	

	@Override
	public String toString() {
		return "DtoComputer [id=" + id 
				+ ", name=" + name 
				+ ", introduced=" + introduced
				+ ", discontinued="	+ discontinued
				+ ", company_id=" + dtocompany.getId()
				+ ", company_name=" + dtocompany.getName() 
				+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((dtocompany == null) ? 0 : dtocompany.hashCode());
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
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (dtocompany == null) {
			if (other.dtocompany != null)
				return false;
		} else if (!dtocompany.equals(other.dtocompany))
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


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}


	public String getDiscontinued() {
		return discontinued;
	}

	public String getCompany_id() {
		return dtocompany.getId();
	}

	public String getCompany_name() {
		return dtocompany.getName();
	}
	
}
