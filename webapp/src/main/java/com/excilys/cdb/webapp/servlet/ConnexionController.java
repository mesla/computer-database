package com.excilys.cdb.webapp.servlet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.core.model.ModelUser;
import com.excilys.cdb.webapp.security.UserDetailsServiceImpl;

@Controller
public class ConnexionController {
	
	private PasswordEncoder passwordEncoder;
	UserDetailsServiceImpl serviceUser;
	public ConnexionController(PasswordEncoder passwordEncoder, UserDetailsServiceImpl serviceUser) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping(value = "/inscription")
	public String displayFormInscription() {
		return "inscription";
	}

	@PostMapping(value = "/inscription")
	public RedirectView inscription (
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
			) {
		serviceUser.insertUser(new ModelUser(null, username, passwordEncoder.encode(password), "ROLE_USER"));
		return new RedirectView("login");
	}
	
	@GetMapping(value="/login")
	public String login() {
		return "login";
	}

}
