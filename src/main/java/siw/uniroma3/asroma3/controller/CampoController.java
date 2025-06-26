package siw.uniroma3.asroma3.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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
import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class CampoController {
	@Autowired
	SportService sportService;
	@Autowired
	CampoService campoService;
	@Autowired
	AssociazioneService associazioneService;
	
	
	@GetMapping("/admin/associazione/{idA}/registra-campo")
	public String registraCampo(@PathVariable ("idA") Long idA ,Model model) {
		model.addAttribute("sports",sportService.getAllSport());
		model.addAttribute("campo", new Campo());
		model.addAttribute("idA",idA);
		return "admin/formCampo.html";
	}
	
	@PostMapping("/admin/associazione/{idA}/registra-campo")
	public String salvaCampo(@Valid @ModelAttribute Campo campo, @PathVariable ("idA") Long idA,
			BindingResult bindingResult, Model model,@RequestParam(value = "file", required = false) MultipartFile file) throws IOException  {
		if (bindingResult.hasErrors()) {
			System.out.println("Errori di validazione sul form di registrazione associazione:");
			bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			return "admin/formCampo.html";
		}
		if (file != null && !file.isEmpty()) {
	        campo.setImmagine(file.getBytes());
	    }
		if (campo.getId() != null && campo.getId() != 0) {
			System.err.println("AVVISO: L'ID del Campo non è null. Hibernate eseguirà un UPDATE invece di un INSERT!");
			
		}
		Associazione associazione= associazioneService.getAssociazione(idA);
		Sport sport = campo.getSport();
		associazione.addSport(sport);
		associazione.addCampo(campo);
		campo.setAssociazione(associazione);
		this.campoService.saveCampo(campo);
		this.associazioneService.saveAssociazione(associazione);
		
		return "redirect:/";
	}
	
	@GetMapping("/associazione/{idA}/sport/{idS}")
	public String mostraCampi(@PathVariable("idA") Long idA,@PathVariable("idS")Long idS, Model model) {
		Sport sport=sportService.getSportByid(idS);
		if(sport==null) {
			return "redirect:/";
		}
		
		model.addAttribute("sport",sport);
		model.addAttribute("campi",campoService.getAllByAssociazioneESport(idA, idS));
		model.addAttribute("associazione",associazioneService.getAssociazione(idA));
		return "campi.html";
		
	}
	
	@GetMapping("/associazione/{idA}/sport/{idS}/campo/{idC}")
	public String mostraCampo(@PathVariable("idA") Long idA,
            @PathVariable("idS") Long idS,@PathVariable("idC") Long id,Model model) {
		Campo campo=campoService.getCampo(id);
		if(campo==null) {
			return "redirect:/";
		}
		model.addAttribute("campo", campo);
		model.addAttribute("sport", campo.getSport());
	    model.addAttribute("associazione", campo.getAssociazione());
		return "campo";
	}

}
