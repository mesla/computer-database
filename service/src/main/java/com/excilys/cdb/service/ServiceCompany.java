package com.excilys.cdb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.core.model.ModelCompany;

@Service
@Transactional
public class ServiceCompany {
		
	private final DaoCompany daoCompany;
	private final DaoComputer daoComputer;
	
	public ServiceCompany (DaoCompany daoCompany, DaoComputer daoComputer) {
		this.daoCompany = daoCompany;
		this.daoComputer = daoComputer;
	}

	public List<ModelCompany> listCompanies() {
		return daoCompany.listCompanies();
	}
	
	@Transactional
	public void delete(Long id) {
		daoComputer.deleteByCompanyId(id);
		daoCompany.delete(id);
	}
}
