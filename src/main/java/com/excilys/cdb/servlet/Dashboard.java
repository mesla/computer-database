package com.excilys.cdb.servlet;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.exception.BadArgumentException;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.servlet.enums.OrderBy;
import com.excilys.cdb.servlet.model.Page;


@Controller
@SessionAttributes("pageInstance")
public class Dashboard {

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
			@RequestParam(value = "orderBy", required=false) String orderByReq,
			@ModelAttribute("pageInstance") Page pageInstance
			) throws RequestFailedException, ConnectionDBFailedException, BadEntryException, BadArgumentException
	{
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
			pageInstance.setAvailablePages(pagination(pageInstance));
			
			model.addAttribute("computerList", serviceComputer.listComputer(pageInstance));
			
			System.out.println(pageInstance.toString());
			
		} catch (NumberFormatException e) {
			throw new BadEntryException("Veuillez vérifier la cohérence des informations fournies");
		}
		return "dashboard";
	}
	
	@ModelAttribute("pageInstance")
	private Page setModelAttr(Model model) {
		return new Page();
	}

	@PostMapping(value = { "/", "/dashboard" })
	public RedirectView doPost(@RequestParam(value = "selection", required=false) String selectionReq) throws BadEntryException, ConnectionDBFailedException, RequestFailedException {
		if (!selectionReq.isEmpty()) {
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
	
	private ArrayList<Integer> pagination(Page pageInstance) {
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
