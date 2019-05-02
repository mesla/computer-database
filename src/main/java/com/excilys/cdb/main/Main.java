package com.excilys.cdb.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.ui.Cli;

public class Main {
	public static void main(String[] args){
		try {
			Cli.displayChoicesList();
		} catch (BadEntryException e) {
			Logger logger = LoggerFactory.getLogger(Main.class);
		    logger.error(e.getMessage());
		}
	}

}
