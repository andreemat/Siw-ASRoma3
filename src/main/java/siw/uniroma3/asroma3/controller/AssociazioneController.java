package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.repository.AssociazioneRepository;
import siw.uniroma3.asroma3.repository.UserRepository;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CredentialsService;
import siw.uniroma3.asroma3.service.UserService;

@Controller
public class AssociazioneController {
	@Autowired
	private AssociazioneService associazioneService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String mostraHome(Model model) {
		model.addAttribute("associazioni", associazioneService.getAllAssociazioni());
		return "home"; // carica home.html
	}

	@GetMapping("/associazione/{id}")

	public String mostraAssociazione(@PathVariable("id")Long id, Model model) {
		Associazione associazione = this.associazioneService.getAssociazione(id);

		model.addAttribute("sports", associazione.getSportList());
		model.addAttribute("associazione", associazione);
		return "associazione.html";
	}

	@GetMapping("/registra-associazione")
	public String registraAssociazione(Model model) {
		
		model.addAttribute("associazione",new Associazione());
		return "formAssociazione.html";
	}

	@PostMapping("/registra-associazione")
	public String registraAssociazione(
			@Valid @ModelAttribute("associazione") Associazione associazione,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			System.out.println("Errori di validazione sul form di registrazione associazione:");
			bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			return "registraAssociazioneForm";
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		associazione.setAdminAssociazione(user);
		user.setAssociazione(associazione);
		associazioneService.saveAssociazione(associazione);
		userService.saveUser(user);
		return "redirect:/";
	}








}
