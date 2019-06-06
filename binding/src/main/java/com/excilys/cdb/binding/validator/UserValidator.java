package com.excilys.cdb.binding.validator;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.DtoUser;
import com.excilys.cdb.core.exception.BadEntryException;
import com.excilys.cdb.core.exception.UnvalidDtoException;

@Component
public class UserValidator {
	public DtoUser checkIntegrity(DtoUser dtoUser) {
		try {
			checkUsernameAndPasswordLength(dtoUser.getUsername(), dtoUser.getPassword());
			return dtoUser;
		} catch (BadEntryException e) {
			throw new UnvalidDtoException("Au moins un des champs n'est pas correct. Veuillez vérifier les informations fournies");
		}
	}
	
	private void checkUsernameAndPasswordLength(String username, String password) {
		if(username.length()<4 || password.length()<4)
			throw new BadEntryException("Le nom d'utilisateur et le mot de passe doivent contenir au moins 4 caractères");
	}
	
}
