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
public class EditComputer {

	private final ServiceComputer serviceComputer;
	private final ServiceCompany serviceCompany;
	private final MapperComputer mapperComputer;
	private final MapperCompany mapperCompany;
	private final ComputerValidator computerValidator;
	
	public EditComputer(
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

	@GetMapping( "/editComputer" )
	public String doGet(Model model, @RequestParam(value = "computerId", required=false) String computerIdReq) {
		try {
			if (computerIdReq != null) {
				Long computerId = Long.valueOf(computerIdReq);
				model.addAttribute("computer", serviceComputer.read(computerId));
			}
			
			ArrayList<DtoCompany> dtoCompanyList = new ArrayList<DtoCompany>();
			serviceCompany.listCompanies().stream()
				.map(x -> mapperCompany.toDto(x))
				.forEach(dtoCompanyList::add);
			model.addAttribute("companyList", dtoCompanyList);
			
			model.addAttribute("companyList", dtoCompanyList);
		} catch (NumberFormatException e) {
			throw new BadEntryException("L'ID renseigné n'est pas un entier");
		}
		return "editComputer";
	}
	
	@PostMapping( "/editComputer" )
	public RedirectView doPost(
			@RequestParam(value="computerName", required=false) String computerNameReq,
			@RequestParam(value="computerId", required=false) String computerIdReq,
			@RequestParam(value="introduced", required=false) String introducedReq,
			@RequestParam(value="discontinued", required=false) String discontinuedReq,
			@RequestParam(value="companyId", defaultValue = "0") String companyIdReq
			) {
		if (!computerNameReq.isEmpty()) {
			try {
				serviceComputer.update(
					mapperComputer.toModel(
						computerValidator.checkIntegrity(
							new DtoComputer(Long.valueOf(computerIdReq),
									computerNameReq,
									introducedReq.isEmpty() ? null : LocalDate.parse(introducedReq),
									discontinuedReq.isEmpty() ? null : LocalDate.parse(discontinuedReq),
									companyIdReq.equals("0") ? null : Long.valueOf(companyIdReq),
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
