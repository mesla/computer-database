package com.excilys.cdb.servlet;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.excilys.cdb.exception.Type403Exception;
import com.excilys.cdb.exception.Type404Exception;
import com.excilys.cdb.exception.Type500Exception;
import com.excilys.cdb.servlet.model.ErrorModel;

@ControllerAdvice
public class ExceptionResolver extends ExceptionHandlerExceptionResolver {
	
	private final String ERROR_PAGE_NAME = "errorPage";
	private final String ERROR_ATTRIBUTE_KEY = "error";
	private final String DEFAULT_ERROR_MSG = "Something unexpected as happened";
	
	
	@ExceptionHandler(value = Exception.class)
	public String treatException(Exception e, Model model) {
		ErrorModel error = new ErrorModel(501, DEFAULT_ERROR_MSG, e.getClass().toString() );
		
		if (e instanceof NoHandlerFoundException || e instanceof Type404Exception) {
			error.setErrorCode(404);
			error.setCustomMessage(e.getMessage());
		} else if (e instanceof Type403Exception) {
			error.setErrorCode(403);
			error.setCustomMessage(e.getMessage());
		} else if(e instanceof Type500Exception) {
			error.setErrorCode(500);
		}
		model.addAttribute(ERROR_ATTRIBUTE_KEY, error);
		return ERROR_PAGE_NAME;
	}
}