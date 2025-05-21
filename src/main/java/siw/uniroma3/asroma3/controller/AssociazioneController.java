package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import siw.uniroma3.asroma3.repository.AssociazioneRepository;

@Controller
public class AssociazioneController {
	@Autowired
	private AssociazioneRepository associazioneRepository;
	
	@GetMapping("/")
    public String mostraHome(Model model) {
        model.addAttribute("associazioni", associazioneRepository.findAll());
        return "home"; // carica home.html
    }
	
	
	
	
	
	
	

}
