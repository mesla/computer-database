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
public class AddComputer {
	ServiceComputer serviceComputer;
	ServiceCompany serviceCompany;
	MapperComputer mapperComputer;
	ComputerValidator computerValidator;
	
	
	public AddComputer(ServiceComputer serviceComputer, ServiceCompany serviceCompany, MapperComputer mapperComputer, ComputerValidator computerValidator) {
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.mapperComputer = mapperComputer;
		this.computerValidator = computerValidator;
	}
	
	@GetMapping( "/addComputer" )
	public String doGet(Model model) throws ConnectionDBFailedException, RequestFailedException {
		this.setModelAttr(model);
		return "AddComputer";
	}

	private void setModelAttr(Model model) throws ConnectionDBFailedException, RequestFailedException {
		model.addAttribute("companyList", serviceCompany.listCompanies());
	}
	
	@PostMapping( "/addComputer" )
	public RedirectView doPost(
			@RequestParam(value = "computerName", required=true) String computerNameReq,
			@RequestParam(value = "introduced", required=false) String introducedReq,
			@RequestParam(value = "discontinued", required=false) String discontinuedReq,
			@RequestParam(value = "companyId", required=false) String companyIdReq
			) throws BadEntryException, RequestFailedException, ConnectionDBFailedException, UnvalidDtoException {
		try {
			if(computerNameReq!=null) {
				serviceComputer.create(
					mapperComputer.toModel(
						computerValidator.checkIntegrity(
							new DtoComputer(null, 
											computerNameReq, 
											introducedReq.isEmpty() ? null : LocalDate.parse(introducedReq), 
											discontinuedReq.isEmpty() ? null : LocalDate.parse(discontinuedReq), 
											companyIdReq.equals("0") ? null : Integer.valueOf(companyIdReq),
											null)
				)));
			}
			return new RedirectView("dashboard");
		} catch (DateTimeParseException | NumberFormatException e) {
			throw new BadEntryException("Veuillez vérifier les informations fournies");
		}
	}
}
