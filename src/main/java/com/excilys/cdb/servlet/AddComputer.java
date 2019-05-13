package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RedirectionException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.exception.UnvalidDtoException;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.ComputerValidator;


@WebServlet(urlPatterns= "/addComputer")
public class AddComputer extends HttpServlet{

	private static final long serialVersionUID = 4504965411432198749L;
	
	private Logger logger = LoggerFactory.getLogger(AddComputer.class);
	private ServiceComputer serviceComputer = ServiceComputer.getInstance();
	private ServiceCompany serviceCompany = ServiceCompany.getInstance();
	
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		try {
			this.setAttributes(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward( request, response );
		} catch (ServletException | IOException | SQLException | ConnectionDBFailedException | RequestFailedException e) {
			logger.error(e.getMessage()+ "\n" + e.getStackTrace().toString());
		}
	}

	private void setAttributes(HttpServletRequest request) throws SQLException, ConnectionDBFailedException, RequestFailedException {
		request.setAttribute("companyList", serviceCompany.listCompanies());
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			if(request.getParameter("computerName")!=null) {
				try {
					serviceComputer.create(
						MapperComputer.getInstance().toModel(
							ComputerValidator.getInstance().checkIntegrity(
								new DtoComputer(null, 
												request.getParameter("computerName"), 
												request.getParameter("introduced").isEmpty() ? null : LocalDate.parse(request.getParameter("introduced")), 
												request.getParameter("discontinued").isEmpty() ? null : LocalDate.parse(request.getParameter("discontinued")), 
												request.getParameter("companyId").equals("0") ? null : Integer.valueOf(request.getParameter("companyId")),
												null)
					)));
				} catch (RequestFailedException | BadEntryException | SQLException | UnvalidDtoException e) {
					logger.warn(e.getMessage() + "\n" + e.getStackTrace().toString());
				}
			}
		} catch(DateTimeParseException e) {
			logger.warn(new BadEntryException("La date entrée n'est pas au format YYYY-mm-DD").getMessage());
		}
		catch(NumberFormatException e) {
			logger.warn(new BadEntryException("L'ID renseigné n'est pas un entier").getMessage() + "\n" + e.getStackTrace().toString());
		}
		try {
			response.sendRedirect(this.getServletContext().getContextPath());
		} catch (IOException e) {
			logger.error(new RedirectionException("Redirection à la page d'accueil échouée dans AddComputer").getMessage() + "\n" + e.getStackTrace().toString());
		}
	}

}
