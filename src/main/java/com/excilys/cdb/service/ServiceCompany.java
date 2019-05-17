package com.excilys.cdb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperCompany;

@Service
public class ServiceCompany {
		
	private final DaoCompany daoCompany;
	private final MapperCompany mapperCompany;
	
	public ServiceCompany (DaoCompany daoCompany, MapperCompany mapperCompany) {
		this.daoCompany = daoCompany;
		this.mapperCompany = mapperCompany;
	}

	public ArrayList<DtoCompany> listCompanies() throws RequestFailedException, ConnectionDBFailedException {
		
		ArrayList<DtoCompany> dtoCompanyList = new ArrayList<DtoCompany>();
		
		daoCompany.listCompanies().stream()
		.map(x -> mapperCompany.toDto(x))
		.forEach(dtoCompanyList::add);
		
		return dtoCompanyList;
	}
	
	public void delete(int id) throws RequestFailedException, ConnectionDBFailedException {
		daoCompany.delete(id);
	}
}
