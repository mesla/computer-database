package com.excilys.cdb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.servlet.model.Page;

@Service
@Transactional
public class ServiceComputer {
		
	private final DaoComputer daoComputer;
	private final DaoCompany daoCompany;
	private final MapperComputer mapperComputer;
	
	public ServiceComputer (DaoCompany daoCompany, DaoComputer daoComputer, MapperComputer mapperComputer) {
		this.daoCompany = daoCompany;
		this.daoComputer = daoComputer;
		this.mapperComputer = mapperComputer;
	}
	
	public ArrayList<DtoComputer> listComputer(Page page){
		
		ArrayList<DtoComputer> dtoComputerList = new ArrayList<DtoComputer>();

		daoComputer.listComputer(page).stream()
			.map(x -> mapperComputer.toDto(x))
			.forEach(dtoComputerList::add);
		
		return dtoComputerList;
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
