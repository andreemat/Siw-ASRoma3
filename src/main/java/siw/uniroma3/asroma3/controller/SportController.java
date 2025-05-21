package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class SportController {
	
	@Autowired 
	private SportService sportService;
	
	@GetMapping("/sport/{id}")
	public String mostraSport(@PathVariable("id") Long id, Model model) {
		Sport sport=sportService.getSportByid(id);
		if(sport==null) {
			return "redirect:/";
		}
		
		model.addAttribute("sport",sport);
		model.addAttribute("campi",sport.getCampi());
		return "campi.html";
		
	}
	
	

}
