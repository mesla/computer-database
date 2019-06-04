package com.excilys.cdb.binding.validator;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.DtoCompany;
import com.excilys.cdb.core.exception.BadEntryException;
import com.excilys.cdb.core.exception.UnvalidDtoException;
import com.excilys.cdb.service.ServiceComputer;

@Component
public class CompanyValidator {
	ServiceComputer serviceComputer;
	
	public CompanyValidator (ServiceComputer serviceComputer) {
		this.serviceComputer = serviceComputer;
	}
	
	public DtoCompany checkIntegrity(DtoCompany dtoCompany) {
		try {
			checkNameIsNotEmptyOrNull(dtoCompany.getName());
			checkCompanyIdExist(dtoCompany.getId());
			return dtoCompany;
		} catch (BadEntryException e) {
			throw new UnvalidDtoException("Un des champs fourni n'est pas correct. Veuillez vérifier les informations fournies");
		}
	}
	
	private void checkNameIsNotEmptyOrNull(String name) {
		if(name == null || name.isEmpty())
			throw new BadEntryException("Le champ 'name' doit être spécifié");
	}
	
	private void checkCompanyIdExist(Long companyId) {
		if(companyId != null)
			serviceComputer.getCompanyById(companyId);
	}
}
