package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperCompany;

public class ServiceCompany {
	
	private static ServiceCompany INSTANCE = null;
	
	private static DaoCompany daoCompany = DaoCompany.getInstance();
	private static MapperCompany mapperCompany = MapperCompany.getInstance();
	
	private ServiceCompany () { }
	
	public static ServiceCompany getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new ServiceCompany(); 
		}
		return INSTANCE;
	}
	
	
	public ArrayList<DtoCompany> listCompanies() throws RequestFailedException, ConnectionDBFailedException {
		
		ArrayList<DtoCompany> dtoCompanyList = new ArrayList<DtoCompany>();
		
		daoCompany.listCompanies().stream()
		.map(x -> mapperCompany.toDto(x))
		.forEach(dtoCompanyList::add);
		
		return dtoCompanyList;
	}
}
