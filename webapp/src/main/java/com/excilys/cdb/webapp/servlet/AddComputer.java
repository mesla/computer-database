package com.excilys.cdb.webapp.servlet;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.binding.dto.DtoCompany;
import com.excilys.cdb.binding.dto.DtoComputer;
import com.excilys.cdb.binding.mapper.MapperCompany;
import com.excilys.cdb.binding.mapper.MapperComputer;
import com.excilys.cdb.binding.validator.ComputerValidator;
import com.excilys.cdb.core.exception.BadEntryException;
import com.excilys.cdb.service.ServiceCompany;
import com.excilys.cdb.service.ServiceComputer;

@Controller
public class AddComputer {
	private final ServiceComputer serviceComputer;
	private final ServiceCompany serviceCompany;
	private final MapperComputer mapperComputer;
	private final MapperCompany mapperCompany;
	private final ComputerValidator computerValidator;
	
	
	public AddComputer(
			ServiceComputer serviceComputer,
			ServiceCompany serviceCompany,
			MapperComputer mapperComputer,
			MapperCompany mapperCompany,
			ComputerValidator computerValidator
	) {
		
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.mapperComputer = mapperComputer;
		this.mapperCompany = mapperCompany;
		this.computerValidator = computerValidator;
	}
	
	@GetMapping( "/addComputer" )
	public String doGet(Model model) {
		
		ArrayList<DtoCompany> dtoCompanyList = new ArrayList<DtoCompany>();
		serviceCompany.listCompanies().stream()
			.map(x -> mapperCompany.toDto(x))
			.forEach(dtoCompanyList::add);
		model.addAttribute("companyList", dtoCompanyList);

		return "addComputer";
	}
	
	@PostMapping( "/addComputer" )
	public RedirectView doPost(
			@RequestParam(value = "computerName") String computerNameReq,
			@RequestParam(value = "introduced", required=false) String introducedReq,
			@RequestParam(value = "discontinued", required=false) String discontinuedReq,
			@RequestParam(value = "companyId", defaultValue = "0") String companyIdReq
			) {
		try {
			if(!computerNameReq.isEmpty()) {
				serviceComputer.create(
					mapperComputer.toModel(
						computerValidator.checkIntegrity(
							new DtoComputer(null, 
											computerNameReq, 
											introducedReq.isEmpty() ? null : LocalDate.parse(introducedReq), 
											discontinuedReq.isEmpty() ? null : LocalDate.parse(discontinuedReq), 
											companyIdReq.equals("0") ? null : Long.valueOf(companyIdReq),
											null)
				)));
			}
			return new RedirectView("dashboard");
		} catch (DateTimeParseException | NumberFormatException e) {
			throw new BadEntryException("Veuillez vérifier les informations fournies");
		}
	}
}
