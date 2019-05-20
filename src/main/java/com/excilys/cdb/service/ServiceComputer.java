package com.excilys.cdb.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.servlet.enums.OrderBy;

@Service
public class ServiceComputer {
		
	private final DaoComputer daoComputer;
	private final DaoCompany daoCompany;
	private final MapperComputer mapperComputer;
	
	public ServiceComputer (DaoCompany daoCompany, DaoComputer daoComputer, MapperComputer mapperComputer) {
		this.daoCompany = daoCompany;
		this.daoComputer = daoComputer;
		this.mapperComputer = mapperComputer;
	}
	
	public ArrayList<DtoComputer> listComputer(int limit, int offset, String sql_like, OrderBy orderby) throws RequestFailedException, ConnectionDBFailedException {
		
		ArrayList<DtoComputer> dtoComputerList = new ArrayList<DtoComputer>();

		daoComputer.listComputer(limit, offset, sql_like, orderby).stream()
			.map(x -> mapperComputer.toDto(x))
			.forEach(dtoComputerList::add);
		
		return dtoComputerList;
	}
		
	public ModelComputer read(int id) throws ConnectionDBFailedException, RequestFailedException {
		return daoComputer.read(id);
	}
	
	public void delete(int id) throws ConnectionDBFailedException, RequestFailedException {
		daoComputer.delete(id);
	}
	
	public void update(ModelComputer modelComputer) throws RequestFailedException, BadEntryException, ConnectionDBFailedException {
		daoComputer.update(modelComputer);
	}
	
	public void create(ModelComputer modelComputer) throws BadEntryException, RequestFailedException, ConnectionDBFailedException {
		daoComputer.create(modelComputer);
	}
	
	public int getNbComputers(String sql_like) throws ConnectionDBFailedException, RequestFailedException {
		return daoComputer.getNbComputers(sql_like);
	}
	
	public ModelCompany getCompanyById(int companyId) throws RequestFailedException, ConnectionDBFailedException, BadEntryException {
		return daoCompany.getCompany(companyId);
	}

}
