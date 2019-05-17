package com.excilys.cdb.servlet;

import javax.servlet.http.HttpServlet;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.AppConfig;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.ComputerValidator;

public abstract class Servlet extends HttpServlet{

	private static final long serialVersionUID = -5315019177348017272L;
	
	protected final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	protected final ServiceComputer serviceComputer = context.getBean(ServiceComputer.class);
	protected final ServiceCompany serviceCompany = context.getBean(ServiceCompany.class);
	protected final MapperComputer mapperComputer = context.getBean(MapperComputer.class);
	protected final ComputerValidator computerValidator = context.getBean(ComputerValidator.class);
}
