package com.excilys.cdb.controller;

//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.excilys.cdb.dto.DtoComputer;
//import com.excilys.cdb.exception.BadEntryException;
//import com.excilys.cdb.exception.ConnectionDBFailedException;
//import com.excilys.cdb.exception.RequestFailedException;
//import com.excilys.cdb.service.ServiceCompany;
//import com.excilys.cdb.service.ServiceComputer;
//import com.excilys.cdb.ui.Cli;
//import com.excilys.cdb.validator.UiValidator;

public class Controller {
//	private Cli ui;
//	private static Logger logger = LoggerFactory.getLogger(Controller.class);
//	private static ServiceComputer serviceComputer = ServiceComputer.getInstance();
//	private static ServiceCompany serviceCompany = ServiceCompany.getInstance();
//	private static UiValidator validator = UiValidator.getInstance();
//	
//	//Lazy Loading
//	private static Controller INSTANCE = null;
//	
//	private Controller() {}
//	
//	public static Controller getInstance()
//    {           
//        if (INSTANCE == null)
//        {   INSTANCE = new Controller(); 
//        }
//        return INSTANCE;
//    }
//	
//	public void callFonction (String choice) throws BadEntryException, SQLException, ConnectionDBFailedException, RequestFailedException {
//		this.ui = new Cli();
//		switch(choice) {
//			case "1":
//				this.listComputers();
//				break;
//			case "2":
//				this.read();
//				break;
//			case "3":
//				this.create();
//				break;
//			case "4":
//				this.delete();
//				break;
//			case "5":
//				this.update();
//				break;
//			case "6":
//				this.listCompanies();
//				break;
//			case "7":
//				break;
//			default:
//				throw new BadEntryException("Mauvais choix");
//		}
//	}
//
//	private void listCompanies() throws RequestFailedException, ConnectionDBFailedException {
//				
//		try {
//			String[] limits = ui.askPage();
//			
//			validator.checkId(limits[0]);
//			validator.checkId(limits[1]);
//			
//			ui.read(serviceCompany.listCompanies(Integer.valueOf(limits[0]), Integer.valueOf(limits[1])));
//			
//		} catch (BadEntryException e) {
//			logger.error(e.getMessage());
//		}
//		
//	}
//
//	private void update() throws SQLException, ConnectionDBFailedException {
//		try {
//
//			int id = ui.askId();
//			DtoComputer oldComputer = serviceComputer.read(id);
//			
//			ArrayList<DtoComputer> DtoComputerList = new ArrayList<DtoComputer>();
//			DtoComputerList.add(oldComputer);
//			ui.read(DtoComputerList);
//			
//			serviceComputer.update(this.ui.createOrUpdate(), oldComputer);
//			
//		} catch (RequestFailedException | BadEntryException e) {
//		    logger.error(e.getMessage());
//		}
//	}
//
//	private void delete() throws BadEntryException {
//		int id = new Cli().askId();
//		serviceComputer.delete(id);
//	}
//
//	private void create() {
//		try {
//			ArrayList<String> args = this.ui.createOrUpdate();
//			if(args.get(0).isEmpty())
//				throw new BadEntryException("Veuillez rentrer une valeur pour le nom");
//			else
//				serviceComputer.create(args);
//		}
//		catch(BadEntryException e) {
//			logger.error(e.getMessage());
//		}
//	}
//
//	private void read() throws SQLException, ConnectionDBFailedException, RequestFailedException, BadEntryException {
//		int id = ui.askId();
//		ArrayList<DtoComputer> DtoComputerList = new ArrayList<DtoComputer>();
//		DtoComputerList.add(serviceComputer.read(id));
//		ui.read(DtoComputerList);
//	}
//
//	private void listComputers() throws RequestFailedException, ConnectionDBFailedException {
//		String[] limits;
//		try {
//			limits = ui.askPage();
//			
//			validator.checkId(limits[0]);
//			validator.checkId(limits[1]);
//			
//			ui.read(serviceComputer.listComputer(Integer.valueOf(limits[0]), Integer.valueOf(limits[1]), null));	
//			
//		} catch (BadEntryException e) {
//			logger.error(e.getMessage());
//		}
//	
//	}
}
