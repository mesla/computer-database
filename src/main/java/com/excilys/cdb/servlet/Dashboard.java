package com.excilys.cdb.servlet;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.exception.BadArgumentException;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.servlet.enums.OrderBy;
import com.excilys.cdb.servlet.model.Page;


@Controller
public class Dashboard {

	private final Page pageInstance = Page.getInstance();
	private final ServiceComputer serviceComputer;
	
	public Dashboard(ServiceComputer serviceComputer) {
		this.serviceComputer = serviceComputer;
	}

	@GetMapping(value = { "/", "/dashboard" })
	public String doGet(
			Model model,
			@RequestParam(value = "page", required=false) String pageReq,
			@RequestParam(value = "search", required=false) String searchReq,
			@RequestParam(value = "size", required=false) String sizeReq,
			@RequestParam(value = "orderBy", required=false) String orderByReq
			) throws RequestFailedException, ConnectionDBFailedException, BadEntryException, BadArgumentException
	{
		this.setPageInstanceParams(pageReq, searchReq, sizeReq, orderByReq);
		this.setModelAttr(model);

		return "dashboard";
	}
	
	private void setPageInstanceParams(String pageReq, String searchReq, String sizeReq, String orderByReq) throws RequestFailedException, ConnectionDBFailedException, BadEntryException, BadArgumentException {
		try {
			if(searchReq != null)
				pageInstance.setLike(searchReq);
			
			if(sizeReq != null) 
				pageInstance.setLimit(Integer.valueOf(sizeReq));
			
			pageInstance.setNbComputers(serviceComputer.getNbComputers(pageInstance.getLike()));
			
			if(pageReq != null) 
					pageInstance.setCurrentPageAndUpdateOffset(Integer.valueOf(pageReq));
			
			if(orderByReq != null) {
				for(OrderBy ob : OrderBy.values()) {
					if(orderByReq.equals(ob.toString())){
						pageInstance.setOrderbyAndResetCurrentPage(ob);				
						break;
					}
				}
			}

			pageInstance.refreshNbPages();
			pageInstance.setAvailablePages(pagination());
		} catch (NumberFormatException e) {
			throw new BadEntryException("Veuillez vérifier la cohérence des informations fournies");
		}
	}
	
	private void setModelAttr(Model model) throws ConnectionDBFailedException, RequestFailedException, BadEntryException, BadArgumentException {
		model.addAttribute("nbPages", pageInstance.getNbPages());
		model.addAttribute("page", pageInstance.getPage());
		model.addAttribute("availablePages", pageInstance.getAvailablePages());
		model.addAttribute("computerList", serviceComputer.listComputer(pageInstance));
		model.addAttribute("nbComputers", pageInstance.getNbComputers());
	}

	@PostMapping(value = { "/", "/dashboard" })
	public RedirectView doPost(@RequestParam(value = "selection", required=false) String selectionReq) throws BadEntryException, ConnectionDBFailedException, RequestFailedException {
		if (selectionReq != null) {
			String listId[] = selectionReq.split(",");
			try {
				for (String id : listId)
					serviceComputer.delete(Integer.valueOf(id));
			} catch (NumberFormatException e1) {
				throw new BadEntryException("Veuillez entrer un id valide");
			}
		}
		return new RedirectView("dashboard");
	}
	
	
	private ArrayList<Integer> pagination() {
		ArrayList<Integer> availablePages = new ArrayList<Integer>();
		int currentPage = pageInstance.getPage();
		int nbPages = pageInstance.getNbPages();
		
		int p = Math.max(3, Math.min (currentPage, nbPages-2));
		  for (int i = p-2 ; i <= p+2 ; i++) {
			  if (i > 0 && i <= nbPages)
		    	 availablePages.add(i);
		  }
		return availablePages;
	}
}
