package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class CampoController {
	@Autowired
	SportService sportService;
	@Autowired
	CampoService campoService;
	
	
	@GetMapping("/admin/registra-campo")
	public String registraCampo(Model model) {
		model.addAttribute("sports",sportService.getAllSport());
		model.addAttribute("campo", new Campo());
		return "admin/formCampo.html";
	}
	
	@PostMapping("/admin/registra-campo")
	public String salvaCampo(@Valid @ModelAttribute Campo campo, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("Errori di validazione sul form di registrazione associazione:");
			bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			return "formCampo.html";
		}
		campoService.saveCampo(campo);
		return "redirect:/";
	}

}
