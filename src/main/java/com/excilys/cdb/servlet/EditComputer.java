package com.excilys.cdb.servlet;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.exception.UnvalidDtoException;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;
import com.excilys.cdb.validator.ComputerValidator;

@Controller
public class EditComputer {

	private final ServiceComputer serviceComputer;
	private final ServiceCompany serviceCompany;
	MapperComputer mapperComputer;
	ComputerValidator computerValidator;
	
	public EditComputer(ServiceComputer serviceComputer, ServiceCompany serviceCompany, MapperComputer mapperComputer,ComputerValidator computerValidator) {
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.mapperComputer = mapperComputer;
		this.computerValidator = computerValidator;
	}

	@GetMapping( "/editComputer" )
	protected String doGet(Model model, @RequestParam(value = "computerId", required=false) String computerIdReq) throws BadEntryException, ConnectionDBFailedException, RequestFailedException{
		try {
			if (computerIdReq != null) {
				Integer computerId = Integer.valueOf(computerIdReq);
				model.addAttribute("computer", serviceComputer.read(computerId));
			}
			model.addAttribute("companyList", serviceCompany.listCompanies());
		} catch (NumberFormatException e) {
			throw new BadEntryException("L'ID renseigné n'est pas un entier");
		}
		return "editComputer";
	}
	
	@PostMapping( "/editComputer" )
	public RedirectView doPost(
			@RequestParam(value="computerName", required=true) String computerNameReq,
			@RequestParam(value="computerId", required=false) String computerIdReq,
			@RequestParam(value="introduced", required=false) String introducedReq,
			@RequestParam(value="discontinued", required=false) String discontinuedReq,
			@RequestParam(value="companyId", required=false) String companyIdReq
			) throws RequestFailedException, BadEntryException, ConnectionDBFailedException, UnvalidDtoException {
		if (computerNameReq != null) {
			try {
				serviceComputer.update(
					mapperComputer.toModel(
						computerValidator.checkIntegrity(
							new DtoComputer(Integer.valueOf(computerIdReq),
									computerNameReq,
									introducedReq.isEmpty() ? null : LocalDate.parse(introducedReq),
									discontinuedReq.isEmpty() ? null : LocalDate.parse(discontinuedReq),
									companyIdReq.equals("0") ? null : Integer.valueOf(companyIdReq),
									null))));
			} catch (DateTimeParseException e) {
				throw new BadEntryException("La date entrée n'est pas au format YYYY-mm-DD");
			} catch (NumberFormatException e) {
				throw new BadEntryException("L'ID renseigné n'est pas un entier");
			}
		}
		return new RedirectView("dashboard");
	}
}
