package com.excilys.cdb.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.exception.BadEntryException;

public class CompanyValidator {
	private static CompanyValidator INSTANCE = null;
	private Logger logger = LoggerFactory.getLogger(CompanyValidator.class);
	
	private CompanyValidator () { }
	
	public static CompanyValidator getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new CompanyValidator(); 
		}
		return INSTANCE;
	}
	
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
