package com.excilys.cdb.service;

import java.util.ArrayList;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.ModelCompany;

public class ServiceCompany {
	
	private static ServiceCompany INSTANCE = null;
	
	private ServiceCompany () { }
	
	public static ServiceCompany getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new ServiceCompany(); 
		}
		return INSTANCE;
	}
	
	
	public ArrayList<DtoCompany> listCompanies(int limit, int offset) throws RequestFailedException, ConnectionDBFailedException {
		
		ArrayList<DtoCompany> dtoCompanyList = new ArrayList<DtoCompany>();
		
		for (ModelCompany company : DaoCompany.getInstance().listCompanies(limit, offset)) {
			dtoCompanyList.add(MapperCompany.getInstance().toDto(company));
		}
		return dtoCompanyList;
	}
}
