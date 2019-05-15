package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.servlet.enums.OrderBy;

public class ServiceComputer {
	
	private static ServiceComputer INSTANCE = null;
	
	private static DaoComputer daoComputer = DaoComputer.getInstance();
	private static DaoCompany daoCompany = DaoCompany.getInstance();
	private static MapperComputer mapperComputer = MapperComputer.getInstance();
	
	private ServiceComputer () { }
	
	public static ServiceComputer getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new ServiceComputer(); 
		}
		return INSTANCE;
	}
	
	public ArrayList<DtoComputer> listComputer(int limit, int offset, String sql_like, OrderBy orderby) throws RequestFailedException, ConnectionDBFailedException {
		
		ArrayList<DtoComputer> dtoComputerList = new ArrayList<DtoComputer>();

		daoComputer.listComputer(limit, offset, sql_like, orderby).stream()
			.map(x -> mapperComputer.toDto(x))
			.forEach(dtoComputerList::add);
		
		return dtoComputerList;
	}
		
	public DtoComputer read(int id) throws SQLException, ConnectionDBFailedException, RequestFailedException {
		return mapperComputer.toDto(daoComputer.read(id));
	}
	
	public void delete(int id) throws ConnectionDBFailedException, SQLException, RequestFailedException {
		daoComputer.delete(id);
	}
	
	public void update(ModelComputer modelComputer) throws RequestFailedException, BadEntryException, SQLException, ConnectionDBFailedException {
		daoComputer.update(modelComputer);
	}
	
	public void create(ModelComputer modelComputer) throws BadEntryException, SQLException, RequestFailedException, ConnectionDBFailedException {
		daoComputer.create(modelComputer);
	}
	
	public int getNbComputers(String sql_like) throws SQLException, ConnectionDBFailedException, RequestFailedException {
		return daoComputer.getNbComputers(sql_like);
	}
	
	public String getCompanyById(int companyId) throws RequestFailedException, ConnectionDBFailedException, BadEntryException {
		return daoCompany.getMatch(companyId);
	}

}
