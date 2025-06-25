package siw.uniroma3.asroma3.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

	

	@GetMapping("/associazione/{id}")

	public String mostraAssociazione(@PathVariable("id")Long id, Model model) {
		Associazione associazione = this.associazioneService.getAssociazione(id);

		model.addAttribute("sports", associazione.getSportList());
		model.addAttribute("associazione", associazione);
		return "associazione.html";
	}

	@GetMapping("/admin/registra-associazione")
	public String registraAssociazione(Model model) {
		
		
		model.addAttribute("associazione",new Associazione());
		return "/admin/formAssociazione.html";
	}

	@PostMapping("/admin/registra-associazione")
	public String registraAssociazione(
			@Valid @ModelAttribute("associazione") Associazione associazione,
			BindingResult bindingResult,
			Model model, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

		if (bindingResult.hasErrors()) {
			System.out.println("Errori di validazione sul form di registrazione associazione:");
			bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			return "admin/registraAssociazioneForm";
		}
		if (file != null && !file.isEmpty()) {
	        // Salva i byte del file dentro l'entità
	        associazione.setImmagine(file.getBytes());
	    }

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		associazione.setAdminAssociazione(user);
		this.associazioneService.addAdminAssociazione(associazione, user);
		this.associazioneService.saveAssociazione(associazione);
		return "redirect:/";
	}
	
	@GetMapping("/admin/associazione/{idA}")
	public String associazioneAdmin(@PathVariable("idA") Long id,Model model) {
		Associazione associazione = this.associazioneService.getAssociazione(id);

		model.addAttribute("sports", associazione.getSportList());
		model.addAttribute("associazione", associazione);
		return "admin/associazioneAdmin";
	}








}
