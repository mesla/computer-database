package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.DaoComputer;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.service.ServiceComputer;

@WebServlet(urlPatterns= "/dashboard")
public class Dashboard extends HttpServlet {
	
	private Page page = Page.getInstance();
	private Logger logger = LoggerFactory.getLogger(DaoComputer.class);
	private ServiceComputer serviceComputer = ServiceComputer.getInstance();
	
	private static final long serialVersionUID = -3858556152838148500L;
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {

		try {
			this.setAttributes(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward( request, response );
		} catch (RequestFailedException | ConnectionDBFailedException | SQLException | ServletException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void setAttributes(HttpServletRequest request) throws SQLException, ConnectionDBFailedException, RequestFailedException {
		
		this.getAttributes(request);
		
		page.setNbComputers(serviceComputer.getNbComputers());
		page.setNbPages(page.getNbComputers()%page.getLimit() == 0 ? page.getNbComputers()/page.getLimit() : page.getNbComputers()/page.getLimit()+1);
		
		ArrayList<Integer> availablePages = new ArrayList<Integer>();
		for(int i = page.getPage()-2; i<=page.getPage()+2; i++) {
			if(i>0 && i<=page.getNbPages()) availablePages.add(i);
		}
		
		page.setAvailablePages(availablePages);
		
		System.out.println(page.toString());
		
		request.setAttribute("nbPages", page.getNbPages());
		request.setAttribute("page", page.getPage());
		request.setAttribute("availablePages", page.getAvailablePages());
		request.setAttribute("nbComputers", serviceComputer.getNbComputers());
		request.setAttribute("computerList", serviceComputer.listComputer(page.getLimit(), page.getOffset()));
	}
	
	private void getAttributes(HttpServletRequest request) throws RequestFailedException, ConnectionDBFailedException {
		if(request.getParameter("page") != null)
			page.setPage(Integer.valueOf(request.getParameter("page")));
		
		if(request.getParameter("size") != null)
			page.setLimit(Integer.valueOf(request.getParameter("size")));
		
		//if(request.getParameter("search") != null)
			//request.setAttribute("computerList", serviceComputer.listComputer(page.getLimit(), page.getOffset(),request.getParameter("search")));
	}
}
