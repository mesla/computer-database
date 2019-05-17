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
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RedirectionException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.exception.UnvalidDtoException;

@WebServlet(urlPatterns = "/editComputer")
public class EditComputer extends Servlet {

	private static final long serialVersionUID = -6242527594276891068L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		try {
			try {
				if (request.getParameter("computerId") != null) {
					Integer computerId = Integer.valueOf(request.getParameter("computerId"));
					request.setAttribute("computer", serviceComputer.read(computerId));
				}
				request.setAttribute("companyList", serviceCompany.listCompanies());
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
			} catch (NumberFormatException e) {
				throw new BadEntryException("L'ID renseigné n'est pas un entier");
			}
		} catch (ServletException | IOException | RequestFailedException | ConnectionDBFailedException | BadEntryException e1) {
			super.errorManager(e1, response);
		}
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println(request.getParameter("computerName"));
			if (request.getParameter("computerName") != null) {
				try {
					serviceComputer.update(
						mapperComputer.toModel(
							computerValidator.checkIntegrity(
								new DtoComputer(Integer.valueOf(request.getParameter("computerId")),
										request.getParameter("computerName"),
										request.getParameter("introduced").isEmpty() ? null : LocalDate.parse(request.getParameter("introduced")),
										request.getParameter("discontinued").isEmpty() ? null : LocalDate.parse(request.getParameter("discontinued")),
										request.getParameter("companyId").equals("0") ? null : Integer.valueOf(request.getParameter("companyId")),
										null))));
				} catch (DateTimeParseException e) {
					throw new BadEntryException("La date entrée n'est pas au format YYYY-mm-DD");
				} catch (NumberFormatException e) {
					throw new BadEntryException("L'ID renseigné n'est pas un entier");
				}
				try {
					response.sendRedirect(this.getServletContext().getContextPath());
				} catch (IOException e) {
					throw new RedirectionException("Redirection à la page d'accueil échouée dans AddComputer");
				}
			}
		} catch (RedirectionException | RequestFailedException | BadEntryException | UnvalidDtoException | ConnectionDBFailedException e) {
			super.errorManager(e, response);
		}

	}
}
