package com.excilys.cdb.validator;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.exception.BadEntryException;

@Component
public class CompanyValidator {
	
	public CompanyValidator () { }
	
	public boolean checkIntegrity(DtoCompany dtoCompany) {
		if(checkNameIsNotEmptyOrNull(dtoCompany.getName()))
			return true;
		else
			return false;
	}
	
	private boolean checkNameIsNotEmptyOrNull(String name) {
		if(name == null || name.isEmpty()) throw new BadEntryException("Le champ 'name' doit être spécifié");
		else return true;

	}
}
