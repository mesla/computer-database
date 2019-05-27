package com.excilys.cdb.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	
	@GetMapping( "/errorPage" )
	private String map(Model model) {
		return "errorPage";
	}
}
