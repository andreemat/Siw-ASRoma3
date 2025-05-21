package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.repository.CampoRepository;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class SportController {
	
	@Autowired 
	private SportService sportService;
	
	@Autowired
	private CampoService campoService;
	
	@GetMapping("associazione/{idA}/sport/{idS}")
	public String mostraSport(@PathVariable("idA") Long idA,@PathVariable("idS")Long idS, Model model) {
		Sport sport=sportService.getSportByid(idS);
		if(sport==null) {
			return "redirect:/";
		}
		
		model.addAttribute("sport",sport);
		model.addAttribute("campi",campoService.getAllByAssociazioneESport(idA, idS));
		return "campi.html";
		
	}
	
	

}
