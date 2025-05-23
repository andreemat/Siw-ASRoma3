package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class PrenotazioneController {
	
	
	@Autowired CampoService campoService;
	@Autowired private AssociazioneService associazioneService;
	@Autowired private SportService sportService;
	private Campo campo;
	
	
	
	@GetMapping("/prenota/campo/{idC}")
	public String formNewPrenotazione(@PathVariable("idC") Long idC,Model model) {
		Campo campo = campoService.getCampo(idC);
		model.addAttribute("associazione",campo.getAssociazione());
		model.addAttribute("sport",campo.getSport());
		model.addAttribute("prenotazione",new Prenotazione());
		model.addAttribute("campo", campo);
		return "formNewPrenotazione.html"; 
		
	}
	
	@PostMapping("/prenota/campo/{idC}")
	public String formNewPrenotazione(@ModelAttribute("prenotazione")Prenotazione prenotazione,@PathVariable("idC") Long idC,Model model) {
		Campo campo = campoService.getCampo(idC);
		model.addAttribute("associazione",campo.getAssociazione());
		model.addAttribute("sport",campo.getSport());
		model.addAttribute("campo", campo);
		model.addAttribute("prenotazione",prenotazione);
		return "formNewPrenotazione.html"; 
	}

}