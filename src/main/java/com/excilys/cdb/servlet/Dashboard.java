package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadArgumentException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RedirectionException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.service.ServiceComputer;

@WebServlet(urlPatterns = "/dashboard")
public class Dashboard extends HttpServlet {

	private Page page = Page.getInstance();
	private Logger logger = LoggerFactory.getLogger(Dashboard.class);
	private ServiceComputer serviceComputer = ServiceComputer.getInstance();

	private static final long serialVersionUID = -3858556152838148500L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			this.setAttributes(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
		} catch (RequestFailedException | ConnectionDBFailedException | SQLException | ServletException
				| IOException e) {
			logger.error(e.getMessage() + "\n" + e.getStackTrace().toString());
		}
	}

	private void setAttributes(HttpServletRequest request)
			throws SQLException, ConnectionDBFailedException, RequestFailedException {

		this.getAttributes(request);

		page.setNbComputers(serviceComputer.getNbComputers(page.getLike()));
		page.setNbPages(page.getNbComputers() % page.getLimit() == 0 ? page.getNbComputers() / page.getLimit()
				: page.getNbComputers() / page.getLimit() + 1);

		ArrayList<Integer> availablePages = new ArrayList<Integer>();
		for (int i = page.getPage() - 2; i <= page.getPage() + 2; i++) {
			if (i > 0 && i <= page.getNbPages())
				availablePages.add(i);
		}

		page.setAvailablePages(availablePages);

		System.out.println(page.toString());

		request.setAttribute("nbComputers", page.getNbComputers());
		request.setAttribute("nbPages", page.getNbPages());
		request.setAttribute("page", page.getPage());
		request.setAttribute("availablePages", page.getAvailablePages());
		request.setAttribute("computerList",
				serviceComputer.listComputer(page.getLimit(), page.getOffset(), page.getLike()));
	}

	private void getAttributes(HttpServletRequest request) throws RequestFailedException, ConnectionDBFailedException, SQLException {
		if(request.getParameter("page") != null) {
			try {
				page.setPage(Integer.valueOf(request.getParameter("page")));
			} catch (NumberFormatException e) {
				logger.warn(new BadArgumentException("L'attribut 'page' fourni dans l'url doit être un entier positif.").getMessage());
			} catch (BadArgumentException e) {
				logger.warn(e.getMessage());
			}
		}
		
		if(request.getParameter("size") != null) {
			try {
				page.setLimit(Integer.valueOf(request.getParameter("size")));
			} catch (NumberFormatException e) {
				logger.warn(new BadArgumentException("L'attribut 'size' fourni dans l'url doit être un entier positif.").getMessage());
			} catch (BadArgumentException e) {
				logger.warn(e.getMessage());
			}
		}

		if(request.getParameter("search") != null)
			try {
				page.setLike(request.getParameter("search"));
			} catch (BadArgumentException e) {
				logger.warn(e.getMessage());
			}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("selection") != null) {
			String listId[] = request.getParameter("selection").split(",");
			for (String id : listId) {
				ServiceComputer.getInstance().delete(Integer.valueOf(id));
			}
			try {
				response.sendRedirect(this.getServletContext().getContextPath());
			} catch (IOException e) {
				logger.error(
						new RedirectionException("Redirection à la page d'accueil échouée dans Dashboard").getMessage()
								+ "\n" + e.getStackTrace().toString());
			}
		}
	}
}
