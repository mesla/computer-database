package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.ui.Cli;

public class Controller {
	private Cli ui;
	private static Logger logger = LoggerFactory.getLogger(Controller.class);
	private static ServiceComputer serviceComputer = ServiceComputer.getInstance();
	private static ServiceCompany serviceCompany = ServiceCompany.getInstance();
	
	//Lazy Loading
	private static Controller INSTANCE = null;
	
	private Controller() {}
	
	public static Controller getInstance()
    {           
        if (INSTANCE == null)
        {   INSTANCE = new Controller(); 
        }
        return INSTANCE;
    }
	
	public void callFonction (String choice) throws BadEntryException, SQLException, ConnectionDBFailedException, RequestFailedException {
		this.ui = new Cli();
		switch(choice) {
			case "1":
				this.listComputers();
				break;
			case "2":
				this.read();
				break;
			case "3":
				this.create();
				break;
			case "4":
				this.delete();
				break;
			case "5":
				this.update();
				break;
			case "6":
				this.listCompanies();
				break;
			case "7":
				break;
			default:
				throw new BadEntryException("Mauvais choix");
		}
	}

	private void listCompanies() throws RequestFailedException, ConnectionDBFailedException, BadEntryException {
		int[] limits = ui.askPage();
		
		ui.read(serviceCompany.listCompanies(limits[0], limits[1]));
	}

	private void update() throws BadEntryException, SQLException, ConnectionDBFailedException {
		try {

			int id = ui.askId();
			
			ArrayList<DtoComputer> DtoComputerList = new ArrayList<DtoComputer>();
			DtoComputerList.add(serviceComputer.read(id));
			ui.read(DtoComputerList);
			
			DtoComputer oldComputer = serviceComputer.read(id);
			serviceComputer.update(this.ui.createOrUpdate(), oldComputer);
			
		} catch (RequestFailedException e) {
		    logger.error(e.getMessage());
		}
	}

	private void delete() throws BadEntryException {
		int id = new Cli().askId();
		serviceComputer.delete(id);
	}

	private void create() throws BadEntryException {
		ArrayList<String> args = this.ui.createOrUpdate();
		if(args.get(0).isEmpty())
			throw new BadEntryException("Veuillez rentrer une valeur pour le nom");
		else
			serviceComputer.create(args);
	}

	private void read() throws SQLException, ConnectionDBFailedException, RequestFailedException, BadEntryException {
		int id = ui.askId();
		ArrayList<DtoComputer> DtoComputerList = new ArrayList<DtoComputer>();
		DtoComputerList.add(serviceComputer.read(id));
		ui.read(DtoComputerList);
	}

	private void listComputers() throws RequestFailedException, ConnectionDBFailedException, BadEntryException {
		int[] limits = ui.askPage();
		ui.read(serviceComputer.listComputer(limits[0], limits[1]));		
	}
}
