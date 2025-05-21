package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.repository.AssociazioneRepository;
import siw.uniroma3.asroma3.service.AssociazioneService;

@Controller
public class AssociazioneController {
	@Autowired
	private AssociazioneService associazioneService;
	
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
	
	
	
	
	
	
	

}
