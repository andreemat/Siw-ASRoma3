package siw.uniroma3.asroma3.controller;


import java.time.LocalTime;
import java.util.Comparator;

import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.PrenotazioneService;
import siw.uniroma3.asroma3.service.SportService;
import siw.uniroma3.asroma3.service.UserService;

@Controller
public class PrenotazioneController {
	
	
	@Autowired CampoService campoService;
	@Autowired private AssociazioneService associazioneService;
	@Autowired private PrenotazioneService prenotazioneService;
	@Autowired private SportService sportService;
	@Autowired private UserService userService;
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
	public String formNewPrenotazione(@ModelAttribute("prenotazione") Prenotazione prenotazione,
	                                  @PathVariable("idC") Long idC,
	                                  @RequestParam(value = "orariSelezionati", required = false) List<String> orariSelezionati,
	                                  Model model) {
	    Campo campo = campoService.getCampo(idC);
	    model.addAttribute("associazione", campo.getAssociazione());
	    model.addAttribute("sport", campo.getSport());
	    model.addAttribute("campo", campo);

	    // Se orari non ancora selezionati → mostra orari disponibili
	    if (orariSelezionati == null || orariSelezionati.isEmpty()) {
	        model.addAttribute("prenotazione", prenotazione);
	        model.addAttribute("slots", prenotazioneService.getSlot(campo, prenotazione.getData()));
	        return "formNewPrenotazione.html";
	    }

	    // Ordina orari (formato: "09:00-10:00")
	    orariSelezionati.sort(Comparator.naturalOrder());

	    String prima = orariSelezionati.get(0).split("-")[0];
	    String ultima = orariSelezionati.get(orariSelezionati.size() - 1).split("-")[1];

	    prenotazione.setOraInizio(LocalTime.parse(prima));
	    prenotazione.setOraFine(LocalTime.parse(ultima));
	    prenotazione.setCampo(campo);

	    // Salva prenotazione
	    prenotazioneService.save(prenotazione);

	    return "redirect:/conferma";
	}






@GetMapping("/utente/prenotazioni")
public String getPrenotazioniUtente(Model model,Principal principal) {
	User user=this.userService.getUserByUsername(principal.getName());
	List<Prenotazione> prenotazioni=this.prenotazioneService.getPrenotazioneByCliente(user);
	model.addAttribute("prenotazioni",prenotazioni);
	return "prenotazioniUtente.html";
	
}
}