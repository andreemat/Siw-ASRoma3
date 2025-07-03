package siw.uniroma3.asroma3.controller;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.model.Citta;
import siw.uniroma3.asroma3.model.Credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import siw.uniroma3.asroma3.service.UserService;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CittaService;
import siw.uniroma3.asroma3.service.CredentialsService;
import java.lang.RuntimeException;
@Controller
public class AuthController {
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CittaService cittaService;

	@Autowired
	private AssociazioneService associazioneService;


	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("citta", this.cittaService.findAll());
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser";
	}

	@PostMapping(value = { "/register" })
	public String registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult userBindingResult, @Valid
			@ModelAttribute("credentials") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {
		if (user.getCitta()==null)
			System.err.println("Citta null");
		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			
			userService.saveUser(user);
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			model.addAttribute("user", user);
			return "account.html";
		}
	  model.addAttribute("citta",cittaService.findAll());
		return "formRegisterUser";
	}

	@GetMapping("/")
	public String mostraHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("allAssociazioni", associazioneService.getAllAssociazioni());
			return "home.html"; 
		}else{
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
				model.addAttribute("associazioni",user.getAssociazioni());
				return "admin/homeAdmin.html";
			}
			else {
				User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
				model.addAttribute("associazioni", this.associazioneService.findAssociazioneByCitta(user.getCitta()));
				model.addAttribute("allAssociazioni", this.associazioneService.getAllAssociazioni());
				return "home.html";}
		}
			
	}






	}
