package com.excilys.cdb.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.CannotFindFileException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.exception.UnvalidDtoException;

@WebServlet(urlPatterns= "/addComputer")
public class AddComputer extends Servlet{

	private static final long serialVersionUID = 4504965411432198749L;

	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		try {
			this.setAttributes(request);
			try {
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward( request, response );
			} catch(IOException e) {
				throw new CannotFindFileException("Cannot find file at : /WEB-INF/views/addComputer.jsp");
			}
		} catch (CannotFindFileException | ServletException | ConnectionDBFailedException | RequestFailedException e1) {
			super.errorManager(e1, response);
		}
	}

	private void setAttributes(HttpServletRequest request) throws ConnectionDBFailedException, RequestFailedException {
		request.setAttribute("companyList", serviceCompany.listCompanies());
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			try {
				if(request.getParameter("computerName")!=null) {
					serviceComputer.create(
						mapperComputer.toModel(
							computerValidator.checkIntegrity(
								new DtoComputer(null, 
												request.getParameter("computerName"), 
												request.getParameter("introduced").isEmpty() ? null : LocalDate.parse(request.getParameter("introduced")), 
												request.getParameter("discontinued").isEmpty() ? null : LocalDate.parse(request.getParameter("discontinued")), 
												request.getParameter("companyId").equals("0") ? null : Integer.valueOf(request.getParameter("companyId")),
												null)
					)));
				}
			} catch (DateTimeParseException | NumberFormatException e) {
				throw new BadEntryException("Veuillez vérifier les informations fournies");
			}
		} catch(BadEntryException | RequestFailedException | ConnectionDBFailedException | UnvalidDtoException e1) {
			super.errorManager(e1, response);
		}
	}

}
