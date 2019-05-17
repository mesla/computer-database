package com.excilys.cdb.validator;

import java.time.LocalDate;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.exception.UnvalidDtoException;
import com.excilys.cdb.service.ServiceComputer;

@Component
public class ComputerValidator {

	private Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
	private ServiceComputer serviceComputer;
	
	public ComputerValidator (ServiceComputer serviceComputer) {
		this.serviceComputer = serviceComputer;
	}
	
	public DtoComputer checkIntegrity(DtoComputer dtoComputer) throws UnvalidDtoException {
		try {
			checkNameIsNotEmptyOrNull(dtoComputer.getName());
			checkDateIntroInfToDiscon(dtoComputer.getIntroduced(), dtoComputer.getDiscontinued());
			checkCompanyIdExist(dtoComputer.getCompanyId());
			
			return dtoComputer;
		}
		catch(BadEntryException e) {
			logger.warn(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
			throw new UnvalidDtoException("Un des champs fourni n'est pas correct. Veuillez vérifier les informations fournies");
		}
	}
	
	private boolean checkNameIsNotEmptyOrNull(String name) throws BadEntryException{
		if(name.isEmpty() || name ==null)
			throw new BadEntryException("Le champ 'name' doit être spécifié");
		else return true;
	}
	
	private boolean checkDateIntroInfToDiscon(LocalDate intro, LocalDate discon) throws BadEntryException {
		if(intro==null || discon==null)
			return true;
		else if(intro.compareTo(discon) <= 0)
			return true;
		else
			throw new BadEntryException("La date 'introduced' doit être inférieure à la date 'discontinued'");
	}
	
	private boolean checkCompanyIdExist(Integer companyId) {
		if(companyId == null)
			return true;
		try {
			serviceComputer.getCompanyById(companyId);
			return true;
		
		} catch (RequestFailedException | ConnectionDBFailedException | BadEntryException e) {
			logger.warn(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
			return false;
		}
	}
}
