package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperComputer;

public class ServiceComputer {
	
	private static ServiceComputer INSTANCE = null;
	
	private Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
	private static DaoComputer daoComputer = DaoComputer.getInstance();
	private static DaoCompany daoCompany = DaoCompany.getInstance();
	private static MapperComputer mapperComputer = MapperComputer.getInstance();
	
	private ServiceComputer () {
		try {
			Dao.initConnection("main");
		} catch (ConnectionDBFailedException e) {
			logger.error(e.getMessage());
		}
	}
	
	public static ServiceComputer getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new ServiceComputer(); 
		}
		return INSTANCE;
	}
	
	public ArrayList<DtoComputer> listComputer(int limit, int offset) throws RequestFailedException, ConnectionDBFailedException {

		ArrayList<DtoComputer> dtoComputerList = new ArrayList<DtoComputer>();
		
		daoComputer.listComputer(limit, offset).stream()
			.map(x -> mapperComputer.toDto(x))
			.forEach(dtoComputerList::add);
				
		return dtoComputerList;
	}
		
	public DtoComputer read(int id) throws SQLException, ConnectionDBFailedException, RequestFailedException {
		return mapperComputer.toDto(daoComputer.read(id));
	}
	
	
	
	public void delete(int id) {
		try {
			daoComputer.delete(id);
		} catch (SQLException | RequestFailedException e) {
		    logger.error(e.getMessage());
		}
	}
	
	public void update(ArrayList<String> args, DtoComputer dtoOldComputer) throws RequestFailedException, BadEntryException {

		try {
	
			DtoComputer dtoComputer = new DtoComputer(
					"0",
					args.get(0),
					args.get(1),
					args.get(2),
					args.get(3),
					(args.get(3) == null || args.get(3).isEmpty()) ? null : daoCompany.getMatch(Integer.valueOf(args.get(3)))
					);
			
			daoComputer.update(mapperComputer.toModel(dtoComputer), mapperComputer.toModel(dtoOldComputer));
				
		} catch (ConnectionDBFailedException | RequestFailedException | SQLException e) {
		    logger.error(e.getMessage());
		}
	
	}
	
	public void create(ArrayList<String> args) throws BadEntryException {
			try {

				DtoComputer dtoComputer = new DtoComputer(
						"0",
						args.get(0),
						args.get(1),
						args.get(2),
						args.get(3),
						(args.get(3) == null || args.get(3).isEmpty()) ? null : daoCompany.getMatch(Integer.valueOf(args.get(3)))
						);
				
				daoComputer.create(mapperComputer.toModel(dtoComputer));
				
			} catch (SQLException | RequestFailedException | ConnectionDBFailedException e) {
			    logger.error(e.getMessage());
			}
	}
	
	public int getNbComputers() throws SQLException, ConnectionDBFailedException, RequestFailedException {
		return daoComputer.getNbComputers();
	}

}
