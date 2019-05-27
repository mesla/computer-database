package com.excilys.cdb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.mapper.MapperCompany;

@Service
public class ServiceCompany {
		
	private final DaoCompany daoCompany;
	private final MapperCompany mapperCompany;
	private final DaoComputer daoComputer;
	
	public ServiceCompany (DaoCompany daoCompany, MapperCompany mapperCompany, DaoComputer daoComputer) {
		this.daoCompany = daoCompany;
		this.mapperCompany = mapperCompany;
		this.daoComputer = daoComputer;
	}

	public ArrayList<DtoCompany> listCompanies() {
		
		ArrayList<DtoCompany> dtoCompanyList = new ArrayList<DtoCompany>();
		
		daoCompany.listCompanies().stream()
		.map(x -> mapperCompany.toDto(x))
		.forEach(dtoCompanyList::add);
		
		return dtoCompanyList;
	}
	
	@Transactional
	public void delete(int id) {
		daoComputer.deleteByCompanyId(id);
		daoCompany.delete(id);
	}
}
