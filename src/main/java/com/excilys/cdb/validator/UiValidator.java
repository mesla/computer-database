package com.excilys.cdb.validator;

import java.util.ArrayList;

import com.excilys.cdb.exception.BadEntryException;

public class UiValidator {
	
	
	private static UiValidator INSTANCE = null;
	
	private UiValidator () { }
	
	public static UiValidator getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new UiValidator(); 
		}
		return INSTANCE;
	}

	
	public boolean checkCreateOrUpdate(ArrayList<String> args) throws BadEntryException {
		if(
				checkDate(args.get(1)) &&
				checkDate(args.get(2)) &&
				checkId(args.get(3))
		)
			return true;
		else return false;
			
	}

	public boolean checkDate(String date) throws BadEntryException {
		if(!date.matches("^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$"))
			if(!date.isEmpty()) 
				throw new BadEntryException("Veuillez rentrer une date au format YYYY-MM-DD");
			else
				return true;
		else return false;
	}
	
	public boolean checkId(String id) throws BadEntryException {
		if(!id.matches("^[0-9]*$"))
			if(!id.isEmpty()) 
				throw new BadEntryException("Veuillez rentrer un entier valide");
			else return true;
		else return false;
	}
	
	
}
