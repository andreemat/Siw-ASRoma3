package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.service.CredentialsService;

@Controller
public class UserController {
	
	@Autowired private CredentialsService credentialsService;

	@GetMapping("/utente/profilo")
	public String ilmioaccount(Model model, @ModelAttribute("userDetails") UserDetails userDetails) {
		 User utente = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		 model.addAttribute("user",utente);
		return "account.html";
	}

}
