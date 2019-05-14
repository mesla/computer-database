package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

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

@WebServlet(urlPatterns = "/editComputer")
public class EditComputer extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(EditComputer.class);
	private static final long serialVersionUID = -6242527594276891068L;
	private final ServiceComputer serviceComputer = ServiceComputer.getInstance();
	private final ServiceCompany serviceCompany = ServiceCompany.getInstance();
	
	private Integer computerId;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getParameter("computerId") != null) {
				this.computerId = Integer.valueOf(request.getParameter("computerId"));
				request.setAttribute("computer", serviceComputer.read(computerId));
			}
			request.setAttribute("companyList", serviceCompany.listCompanies());

		} catch (SQLException | ConnectionDBFailedException | RequestFailedException e) {
			logger.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
		} catch (NumberFormatException e) {
			logger.warn(new BadEntryException("L'ID renseigné n'est pas un entier").getMessage() + "\n"
					+ Arrays.toString(e.getStackTrace()));
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println(request.getParameter("computerName"));
			if (request.getParameter("computerName") != null) {
				try {
					serviceComputer.update(
						MapperComputer.getInstance().toModel(
							ComputerValidator.getInstance().checkIntegrity(
								new DtoComputer(computerId,
										request.getParameter("computerName"),
										request.getParameter("introduced").isEmpty() ? null : LocalDate.parse(request.getParameter("introduced")),
										request.getParameter("discontinued").isEmpty() ? null : LocalDate.parse(request.getParameter("discontinued")),
										request.getParameter("companyId").equals("0") ? null : Integer.valueOf(request.getParameter("companyId")),
										null))));
				} catch (RequestFailedException | BadEntryException | SQLException | UnvalidDtoException | ConnectionDBFailedException e) {
					logger.warn(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
				}
			}
		} catch (DateTimeParseException e) {
			logger.warn(new BadEntryException("La date entrée n'est pas au format YYYY-mm-DD").getMessage() + "\n"
					+ Arrays.toString(e.getStackTrace()));
		} catch (NumberFormatException e) {
			logger.warn(new BadEntryException("L'ID renseigné n'est pas un entier").getMessage() + "\n"
					+ Arrays.toString(e.getStackTrace()));
		}
		try {
			response.sendRedirect(this.getServletContext().getContextPath());
		} catch (IOException e) {
			logger.error(
					new RedirectionException("Redirection à la page d'accueil échouée dans AddComputer").getMessage()
							+ "\n" + Arrays.toString(e.getStackTrace()));
		}
	}
}
