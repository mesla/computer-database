package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadArgumentException;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.CannotFindFileException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.servlet.enums.OrderBy;
import com.excilys.cdb.servlet.model.Page;

@WebServlet(urlPatterns = "/dashboard")
public class Dashboard extends Servlet {

	private final Page page = Page.getInstance();

	private static final long serialVersionUID = -3858556152838148500L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		try {
			this.setAttributes(request);
			try{
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
			} catch(IOException e) {
				throw new CannotFindFileException("Cannot find file at : /WEB-INF/views/addComputer.jsp");
			}
		} catch (CannotFindFileException | ServletException | RequestFailedException | ConnectionDBFailedException | BadEntryException | BadArgumentException e) {
			super.errorManager(e, response);
		}
	}

	private void setAttributes(HttpServletRequest request) throws ConnectionDBFailedException, RequestFailedException, BadEntryException, BadArgumentException {

		this.getAttributes(request);
		
		ArrayList<DtoComputer> dtoComputerList = serviceComputer.listComputer(page.getLimit(), page.getOffset(), page.getLike(), page.getOrderBy());
		page.setNbComputers(serviceComputer.getNbComputers(page.getLike()));
		
		page.refreshNbPages();

		page.setAvailablePages(pagination());
		
		request.setAttribute("nbPages", page.getNbPages());
		request.setAttribute("page", page.getPage());
		request.setAttribute("availablePages", page.getAvailablePages());
		request.setAttribute("computerList", dtoComputerList);
		request.setAttribute("nbComputers", page.getNbComputers());

		System.out.println(page.toString());

	}

	private void getAttributes(HttpServletRequest request) throws RequestFailedException, ConnectionDBFailedException, BadEntryException, BadArgumentException {
		try {
			if(request.getParameter("page") != null) 
					page.setPage(Integer.valueOf(request.getParameter("page")));
	
			if(request.getParameter("size") != null) 
				page.setLimit(Integer.valueOf(request.getParameter("size")));
	
			if(request.getParameter("search") != null)
				page.setLike(request.getParameter("search"));
	
			
			if(request.getParameter("orderBy") != null)
					for(OrderBy ob : OrderBy.values()) {
						if(request.getParameter("orderBy").equals(ob.toString())){
							page.setOrderby(ob);				
							break;
						}
					}
		} catch (NumberFormatException e) {
			throw new BadEntryException("Veuillez vérifier la cohérence des informations fournies");
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getParameter("selection") != null) {
				String listId[] = request.getParameter("selection").split(",");
				
				try {
					for (String id : listId)
						serviceComputer.delete(Integer.valueOf(id));
				} catch (NumberFormatException e1) {
					throw new BadEntryException("Veuillez entrer un id valide");
				}
				
				response.sendRedirect(this.getServletContext().getContextPath());
			}
		} catch (BadEntryException | RequestFailedException | IOException | ConnectionDBFailedException e) {
			super.errorManager(e, response);
		}
	}
	
	private ArrayList<Integer> pagination() {
		
		ArrayList<Integer> availablePages = new ArrayList<Integer>();
		int currentPage = page.getPage();
		int nbPages = page.getNbPages();
		
		int p = Math.max(3, Math.min (currentPage, nbPages-2));
		  for (int i = p-2 ; i <= p+2 ; i++) {
			  if (i > 0 && i <= nbPages)
		    	 availablePages.add(i);
		  }

		return availablePages;
	}
}
