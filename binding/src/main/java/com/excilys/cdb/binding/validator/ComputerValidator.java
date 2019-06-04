package com.excilys.cdb.binding.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.DtoComputer;
import com.excilys.cdb.core.exception.BadEntryException;
import com.excilys.cdb.core.exception.UnvalidDtoException;
import com.excilys.cdb.service.ServiceComputer;

@Component
public class ComputerValidator {

	private ServiceComputer serviceComputer;
	
	public ComputerValidator (ServiceComputer serviceComputer) {
		this.serviceComputer = serviceComputer;
	}
	
	public DtoComputer checkIntegrity(DtoComputer dtoComputer) {
		try {
			checkNameIsNotEmptyOrNull(dtoComputer.getName());
			checkDateIntroInfToDiscon(dtoComputer.getIntroduced(), dtoComputer.getDiscontinued());
			checkCompanyIdExist(dtoComputer.getCompanyId());
			
			return dtoComputer;
		}
		catch(BadEntryException e) {
			throw new UnvalidDtoException("Un des champs fourni n'est pas correct. Veuillez vérifier les informations fournies");
		}
	}
	
	private void checkNameIsNotEmptyOrNull(String name) {
		if (name ==null || name.isEmpty())
			throw new BadEntryException("Le champ 'name' doit être spécifié");
	}
	
	private void checkDateIntroInfToDiscon(LocalDate intro, LocalDate discon)  {
		if((intro!=null && discon!=null) && intro.compareTo(discon) > 0)
			throw new BadEntryException("La date 'introduced' doit être inférieure à la date 'discontinued'");		
	}
	
	private void checkCompanyIdExist(Long companyId) {
		if(companyId != null)
			serviceComputer.getCompanyById(companyId);
	}
}
