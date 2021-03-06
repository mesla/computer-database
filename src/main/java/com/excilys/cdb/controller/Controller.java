package com.excilys.cdb.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.config.AppConfig;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;

public class Controller {
	private static Controller INSTANCE = null;
	private static Logger logger = LoggerFactory.getLogger(Controller.class);
	protected final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	protected final ServiceComputer serviceComputer = context.getBean(ServiceComputer.class);
	protected final ServiceCompany serviceCompany = context.getBean(ServiceCompany.class);
	
	private Controller() { }
	
	public static Controller getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Controller();
		}
		return INSTANCE;
	}
	
	public void deleteCompany(String idStr) {
		try {
			Long id = Long.valueOf(idStr);
			serviceCompany.delete(id);
		} catch (NumberFormatException e) {
			logger.warn(new BadEntryException("Vous n'avez pas entré un entier ").getMessage());
		} catch (RequestFailedException e1) {
			logger.warn(e1.getMessage() + "\n" + Arrays.asList(e1.getStackTrace()));
		} catch (ConnectionDBFailedException e2) {
			logger.error(e2.getMessage() + "\n" + Arrays.asList(e2.getStackTrace()));
		}
	}
/*
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

	private void listCompanies() throws RequestFailedException, ConnectionDBFailedException {
				
		try {
			String[] limits = ui.askPage();
			
			validator.checkId(limits[0]);
			validator.checkId(limits[1]);
			
			ui.read(serviceCompany.listCompanies(Integer.valueOf(limits[0]), Integer.valueOf(limits[1])));
			
		} catch (BadEntryException e) {
			logger.error(e.getMessage());
		}
		
	}

	private void update() throws SQLException, ConnectionDBFailedException {
		try {

			int id = ui.askId();
			DtoComputer oldComputer = serviceComputer.read(id);
			
			ArrayList<DtoComputer> DtoComputerList = new ArrayList<DtoComputer>();
			DtoComputerList.add(oldComputer);
			ui.read(DtoComputerList);
			
			serviceComputer.update(this.ui.createOrUpdate(), oldComputer);
			
		} catch (RequestFailedException | BadEntryException e) {
		    logger.error(e.getMessage());
		}
	}

	private void delete() throws BadEntryException {
		int id = new Cli().askId();
		serviceComputer.delete(id);
	}

	private void create() {
		try {
			ArrayList<String> args = this.ui.createOrUpdate();
			if(args.get(0).isEmpty())
				throw new BadEntryException("Veuillez rentrer une valeur pour le nom");
			else
				serviceComputer.create(args);
		}
		catch(BadEntryException e) {
			logger.error(e.getMessage());
		}
	}


	private void listComputers() throws RequestFailedException, ConnectionDBFailedException {
		String[] limits;
		try {
			limits = ui.askPage();
			
			validator.checkId(limits[0]);
			validator.checkId(limits[1]);
			
			ui.read(serviceComputer.listComputer(Integer.valueOf(limits[0]), Integer.valueOf(limits[1]), null));	
			
		} catch (BadEntryException e) {
			logger.error(e.getMessage());
		}
	
	}
*/
}
