package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet(urlPatterns= "/addComputer")
public class AddComputer extends Servlet{

	private static final long serialVersionUID = 4504965411432198749L;
	private final Logger logger = LoggerFactory.getLogger(AddComputer.class);

	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		try {
			this.setAttributes(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward( request, response );
		} catch (ServletException | IOException | SQLException | ConnectionDBFailedException | RequestFailedException e) {
			logger.error(e.getMessage()+ "\n" + Arrays.toString(e.getStackTrace()));
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
						mapperComputer.toModel(
							computerValidator.checkIntegrity(
								new DtoComputer(null, 
												request.getParameter("computerName"), 
												request.getParameter("introduced").isEmpty() ? null : LocalDate.parse(request.getParameter("introduced")), 
												request.getParameter("discontinued").isEmpty() ? null : LocalDate.parse(request.getParameter("discontinued")), 
												request.getParameter("companyId").equals("0") ? null : Integer.valueOf(request.getParameter("companyId")),
												null)
					)));
					try {
						response.sendRedirect(this.getServletContext().getContextPath());
					} catch (IOException e) {
						logger.error(new RedirectionException("Redirection à la page d'accueil échouée dans AddComputer").getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
					}
				} catch (ConnectionDBFailedException e) {
					logger.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
				} catch (RequestFailedException | BadEntryException | SQLException | UnvalidDtoException e1) {
					logger.warn(e1.getMessage() + "\n" + Arrays.toString(e1.getStackTrace()));
					try {
						response.sendError(500, e1.getMessage());
					} catch (IOException e) {
						logger.error(new RedirectionException("Redirection vers la page d'erreur 500 échouée").getMessage());
					}
				}
			}
		} catch(DateTimeParseException e) {
			logger.warn(new BadEntryException("La date entrée n'est pas au format YYYY-mm-DD").getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
		} catch(NumberFormatException e) {
			logger.warn(new BadEntryException("L'ID renseigné n'est pas un entier").getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
		}

	}

}
