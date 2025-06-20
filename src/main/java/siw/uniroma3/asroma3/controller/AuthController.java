package siw.uniroma3.asroma3.controller;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.model.Credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import siw.uniroma3.asroma3.service.UserService;
import siw.uniroma3.asroma3.service.CredentialsService;

@Controller
public class AuthController {
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;
	
    
	@GetMapping("/login")
    public String login() {
        return "login";
    }
	
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
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

		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "registrationSuccessful";
        }
        return "registerUser";
    }
	
	
	



}
