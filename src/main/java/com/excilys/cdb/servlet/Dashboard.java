package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.service.ServiceComputer;

public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = -3858556152838148500L;
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		int size = 10;
		int page = 1;
		
		int offset = (page-1) * size;
		try {
			request.setAttribute("computerList", ServiceComputer.getInstance().listComputer(size, offset));
		} catch (RequestFailedException | ConnectionDBFailedException e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward( request, response );
	}
}
