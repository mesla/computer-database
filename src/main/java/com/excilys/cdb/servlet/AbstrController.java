package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.exception.Type500Exception;
import com.excilys.cdb.exception.Type403Exception;
import com.excilys.cdb.exception.Type404Exception;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.ComputerValidator;

public abstract class AbstrController {
	
//	protected final ServiceComputer serviceComputer = context.getBean(ServiceComputer.class);
//	protected final ServiceCompany serviceCompany = context.getBean(ServiceCompany.class);
//	protected final MapperComputer mapperComputer = context.getBean(MapperComputer.class);
//	protected final ComputerValidator computerValidator = context.getBean(ComputerValidator.class);
	private final Logger logger = LoggerFactory.getLogger(AbstrController.class);

	protected void errorManager(Exception e) {
		if(e instanceof ServletException || e instanceof IOException || e instanceof Type500Exception) {
			logger.error(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
			//response.sendError(500, e.getMessage());
		}
		else if(e instanceof Type403Exception) {
			logger.warn(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
			//response.sendError(403, e.getMessage());
		} else if(e instanceof Type404Exception) {
			logger.error(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
			//response.sendError(404, e.getMessage());
		}
		else {
			logger.error(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
			//response.sendError(501, e.getMessage());
		}
	}
}