package com.excilys.cdb.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperCompany;

public class ServiceCompany {
	
	private static ServiceCompany INSTANCE = null;
	
	private Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
	private static DaoCompany daoCompany = DaoCompany.getInstance();
	private static MapperCompany mapperCompany = MapperCompany.getInstance();
	
	private ServiceCompany () {
		try {
			Dao.initConnection("main");
		} catch (ConnectionDBFailedException e) {
			logger.error(e.getMessage());
		}
	}
	
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
