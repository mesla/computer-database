package com.excilys.cdb.core.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="computer")
public class ModelComputer{
	
	public ModelComputer() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name= "name")
	private String name;
	@Column(name= "introduced")
	private Timestamp introduced;
	@Column(name= "discontinued")
	private Timestamp discontinued;
	
	@ManyToOne
	@JoinColumn(name="company_id", referencedColumnName = "id")
	private ModelCompany modelCompany;
	
	public ModelComputer(Long pId, String pName, Timestamp pIntroduced, Timestamp pDiscontinued, ModelCompany modelCompany) {
		this.id = pId;
		this.name = pName;
		this.introduced = pIntroduced;
		this.discontinued = pDiscontinued;
		this.modelCompany = modelCompany;
	}

	public Long getId() {
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

	public Long getCompanyId() {
		return modelCompany==null ? null : modelCompany.getId();
	}

	public String getCompanyName() {
		return modelCompany==null ? null : modelCompany.getName();
	}

	public ModelCompany getModelCompany() {
		return modelCompany;
	}

	public void setModelCompany(ModelCompany modelCompany) {
		this.modelCompany = modelCompany;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

}
