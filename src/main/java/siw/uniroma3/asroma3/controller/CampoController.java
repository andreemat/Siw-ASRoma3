package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class CampoController {

	
	@Autowired CampoService campoService;
	@Autowired private AssociazioneService associazioneService;
	@Autowired private SportService sportService;
	
	
	
	@GetMapping("/associazione/{idA}/sport/{idS}/campo/{idC}")
	public String formNewPrenotazione(@PathVariable("idA") Long idA, @PathVariable("idS") Long idS,@PathVariable("idC") Long idC,Model model) {
		model.addAttribute("associazione",associazioneService.getAssociazione(idA));
		model.addAttribute("sport",sportService.getSportByid(idS));
		model.addAttribute("prenotazione",new Prenotazione());
		model.addAttribute("campo", campoService.getCampo(idC));
		return "formNewPrenotazione.html"; 
		
	}
	
	@PostMapping("/associazione/{idA}/sport/{idS}/campo/{idC}")
	public String formNewPrenotazione(@PathVariable("idA") Long idA, @PathVariable("idS") Long idS,@ModelAttribute("prenotazione")Prenotazione prenotazione,@PathVariable("idC") Long idC,Model model) {
		model.addAttribute("associazione",associazioneService.getAssociazione(idA));
		model.addAttribute("sport",sportService.getSportByid(idS));
		model.addAttribute("prenotazione",prenotazione);
		model.addAttribute("campo", campoService.getCampo(idC));
		return "formNewPrenotazione.html"; 
	}
}
