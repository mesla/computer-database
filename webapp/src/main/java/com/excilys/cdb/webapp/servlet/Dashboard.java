package com.excilys.cdb.webapp.servlet;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.binding.dto.DtoComputer;
import com.excilys.cdb.binding.mapper.MapperComputer;
import com.excilys.cdb.core.exception.BadEntryException;
import com.excilys.cdb.core.model.Page;
import com.excilys.cdb.enums.OrderBy;
import com.excilys.cdb.service.ServiceComputer;

@Controller
@SessionAttributes("pageInstance")
public class Dashboard {

	private final ServiceComputer serviceComputer;
	private final MapperComputer mapperComputer;
	
	public Dashboard(ServiceComputer serviceComputer, MapperComputer mapperComputer) {
		this.serviceComputer = serviceComputer;
		this.mapperComputer = mapperComputer;
	}

	@GetMapping(value = { "/", "/dashboard" })
	public String doGet(
			Model model,
			@RequestParam(value = "page", required=false) String pageReq,
			@RequestParam(value = "search", required=false) String searchReq,
			@RequestParam(value = "size", required=false) String sizeReq,
			@RequestParam(value = "orderBy", required=false) String orderByReq,
			@RequestParam(value = "reset", required=false) String reset,
			@ModelAttribute("pageInstance") Page pageInstance,
			SessionStatus sessionStatus
			) {
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
			
			ArrayList<DtoComputer> dtoComputerList = new ArrayList<DtoComputer>();
			serviceComputer.listComputer(pageInstance).stream()
				.map(x -> mapperComputer.toDto(x))
				.forEach(dtoComputerList::add);
			model.addAttribute("computerList", dtoComputerList);
						
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
	public RedirectView doPost(@RequestParam(value = "selection", required=false) String selectionReq) {
		if (!selectionReq.isEmpty()) {
			String listId[] = selectionReq.split(",");
			try {
				for (String id : listId)
					serviceComputer.delete(Long.valueOf(id));
			} catch (NumberFormatException e1) {
				throw new BadEntryException("Veuillez entrer un id valide");
			}
		}
		return new RedirectView("dashboard");
	}
	
	private ArrayList<Long> pagination(Page pageInstance) {
		ArrayList<Long> availablePages = new ArrayList<Long>();
		int currentPage = pageInstance.getPage();
		long nbPages = pageInstance.getNbPages();
		
		long p = Math.max(3, Math.min (currentPage, nbPages-2));
		  for (long i = p-2 ; i <= p+2 ; i++) {
			  if (i > 0 && i <= nbPages)
		    	 availablePages.add(i);
		  }
		return availablePages;
	}
	
	@GetMapping(value = "/closeSession")
	public RedirectView closeSession (SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return new RedirectView("dashboard");
	}
}
