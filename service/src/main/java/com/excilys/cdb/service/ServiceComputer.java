package com.excilys.cdb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.core.model.ModelCompany;
import com.excilys.cdb.core.model.ModelComputer;
import com.excilys.cdb.core.model.Page;

@Service
@Transactional
public class ServiceComputer {
		
	private final DaoComputer daoComputer;
	private final DaoCompany daoCompany;
	
	public ServiceComputer (DaoCompany daoCompany, DaoComputer daoComputer) {
		this.daoCompany = daoCompany;
		this.daoComputer = daoComputer;
	}
	
	public List<ModelComputer> listComputer(Page page){
		return daoComputer.listComputer(page);
	}
		
	public ModelComputer read(Long id) {
		return daoComputer.read(id);
	}
	
	public void delete(Long id) {
		daoComputer.delete(id);
	}
	
	public void update(ModelComputer modelComputer) {
		daoComputer.update(modelComputer);
	}
	
	public void create(ModelComputer modelComputer) {
		daoComputer.create(modelComputer);
	}
	
	public long getNbComputers(String sql_like) {
		return daoComputer.getNbComputers(sql_like);
	}
	
	public ModelCompany getCompanyById(Long companyId) {
		return daoCompany.getCompany(companyId);
	}

}
