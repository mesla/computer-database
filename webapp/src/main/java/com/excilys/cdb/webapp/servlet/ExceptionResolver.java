package com.excilys.cdb.webapp.servlet;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.excilys.cdb.core.exception.Type403Exception;
import com.excilys.cdb.core.exception.Type404Exception;
import com.excilys.cdb.core.exception.Type500Exception;
import com.excilys.cdb.core.model.ErrorModel;

@ControllerAdvice
public class ExceptionResolver extends ExceptionHandlerExceptionResolver {
	
	private final Logger myLogger = LoggerFactory.getLogger(this.getClass());
	
	private final String ERROR_PAGE_NAME = "errorPage";
	private final String ERROR_ATTRIBUTE_KEY = "error";
	private final String DEFAULT_ERROR_MSG = "Something unexpected as happened";
	
	
	@ExceptionHandler(value = Exception.class)
	public String treatException(Exception e, Model model) {
		ErrorModel error = new ErrorModel(501, DEFAULT_ERROR_MSG, e.getClass().toString() );
		
		if (e instanceof NoHandlerFoundException || e instanceof Type404Exception) {
			error.setErrorCode(404);
			error.setCustomMessage(e.getMessage());
			myLogger.error(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
		} else if (e instanceof Type403Exception) {
			error.setErrorCode(403);
			error.setCustomMessage(e.getMessage());
			myLogger.warn(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
		} else if(e instanceof Type500Exception) {
			error.setErrorCode(500);
			myLogger.error(e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
		} else {
			myLogger.error("ERREUR NON GEREE \n" + e.getMessage() + "\n" + Arrays.asList(e.getStackTrace()));
		}
		model.addAttribute(ERROR_ATTRIBUTE_KEY, error);
		return ERROR_PAGE_NAME;
	}
}