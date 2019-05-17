package com.excilys.cdb.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.exception.BadEntryException;

@Component
public class CompanyValidator {

	private Logger logger = LoggerFactory.getLogger(CompanyValidator.class);
	
	public CompanyValidator () { }
	
	public boolean checkIntegrity(DtoCompany dtoCompany){
		if(checkNameIsNotEmptyOrNull(dtoCompany.getName()))
			return true;
		else
			return false;
	}
	
	private boolean checkNameIsNotEmptyOrNull(String name){
		try {
			if(name == null || name.isEmpty()) throw new BadEntryException("Le champ 'name' doit être spécifié");
			return true;
		} catch (BadEntryException e) {
			logger.warn(e.getMessage());
			return false;
		}
	}
}
