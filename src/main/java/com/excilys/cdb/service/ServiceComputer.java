package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.DaoCompany;
import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.ModelComputer;

public class ServiceComputer {
	
	private static ServiceComputer INSTANCE = null;
	
	private ServiceComputer () { }
	
	public static ServiceComputer getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new ServiceComputer(); 
		}
		return INSTANCE;
	}
	
	public ArrayList<DtoComputer> listComputer(int limit, int offset) throws RequestFailedException, ConnectionDBFailedException {

		ArrayList<DtoComputer> dtoComputerList = new ArrayList<DtoComputer>();
		
		for (ModelComputer computer : DaoComputer.getInstance().listComputer(limit, offset)) {
			dtoComputerList.add(MapperComputer.getInstance().toDto(computer));
		}
		return dtoComputerList;
	}
	
	
	
	public DtoComputer read(int id) throws SQLException, ConnectionDBFailedException, RequestFailedException {
		
		return MapperComputer.getInstance().toDto(DaoComputer.getInstance().read(id));
	}
	
	
	
	public void delete(int id) {
		try {
			DaoComputer.getInstance().delete(id);
		} catch (SQLException | RequestFailedException e) {
			System.out.println(e.getMessage());
			Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
		    logger.info(e.getMessage());
		}
	}
	
	public void update(ArrayList<String> args, DtoComputer dtoOldComputer) throws RequestFailedException, BadEntryException {
		
		DtoCompany dtoCompany;
		try {
			dtoCompany = new DtoCompany(
					args.get(3),
					(args.get(3) == null || args.get(3).isEmpty()) ? null : DaoCompany.getInstance().getMatch(Integer.valueOf(args.get(3))));
	
			DtoComputer dtoComputer = new DtoComputer(
					"0",
					args.get(0),
					args.get(1),
					args.get(2),
					dtoCompany);
			
			DaoComputer.getInstance().update(MapperComputer.getInstance().toModel(dtoComputer), MapperComputer.getInstance().toModel(dtoOldComputer));
				
		} catch (ConnectionDBFailedException | RequestFailedException | SQLException e) {
			Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
		    logger.info(e.getMessage());
			System.out.println(e.getMessage());
		}
	
	}
	
	public void create(ArrayList<String> args) throws BadEntryException {
			try {
				DtoCompany dtoCompany;

				dtoCompany = new DtoCompany(
						args.get(3),
						(args.get(3) == null || args.get(3).isEmpty()) ? null : DaoCompany.getInstance().getMatch(Integer.valueOf(args.get(3))));

				DtoComputer dtoComputer = new DtoComputer(
						"0",
						args.get(0),
						args.get(1),
						args.get(2),
						dtoCompany);
				
				DaoComputer.getInstance().create(MapperComputer.getInstance().toModel(dtoComputer));
				
			} catch (SQLException | RequestFailedException | ConnectionDBFailedException e) {
				Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
			    logger.info(e.getMessage());
				System.out.println(e.getMessage());
			}
		

	}
}
