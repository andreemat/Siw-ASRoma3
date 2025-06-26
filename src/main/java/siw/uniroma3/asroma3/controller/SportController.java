package siw.uniroma3.asroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.repository.CampoRepository;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class SportController {
	
	@Autowired 
	private SportService sportService;
	
	@Autowired
	private CampoService campoService;
	
	@Autowired
	private AssociazioneService associazioneService;
	
	
	
	
	

}
