package siw.uniroma3.asroma3.controller;


import java.time.LocalTime;
import java.util.Comparator;

import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import siw.uniroma3.asroma3.service.CredentialsService;
import siw.uniroma3.asroma3.service.PrenotazioneService;
import siw.uniroma3.asroma3.service.SportService;
import siw.uniroma3.asroma3.service.UserService;

@Controller
public class PrenotazioneController {
	
	
	@Autowired CampoService campoService;
	@Autowired private AssociazioneService associazioneService;
	@Autowired private PrenotazioneService prenotazioneService;
	@Autowired private CredentialsService credentialsService;
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
		return "formDataPrenotazione.html"; 
		
	}
    @PostMapping("/prenota/campo/{idC}/giorno")
    public String selezionaGiorno(@ModelAttribute("prenotazione") Prenotazione prenotazione,
                                  @PathVariable("idC") Long idC,
                                  Model model) {
        Campo campo = campoService.getCampo(idC);
        model.addAttribute("campo", campo);
        model.addAttribute("associazione", campo.getAssociazione());
        model.addAttribute("sport", campo.getSport());
        model.addAttribute("prenotazione", prenotazione);
        model.addAttribute("slots", prenotazioneService.getSlot(campo, prenotazione.getData()));
        return "formOrariPrenotazione.html";
    }

    // SECONDO STEP: Conferma prenotazione
    @PostMapping("/prenota/campo/{idC}/conferma")
    public String confermaPrenotazione(@ModelAttribute("prenotazione") Prenotazione prenotazione,
                                       @PathVariable("idC") Long idC,
                                       @RequestParam("orariSelezionati") List<String> orariSelezionati) {

        Campo campo = campoService.getCampo(idC);

        orariSelezionati.sort(Comparator.naturalOrder());
        String inizio = orariSelezionati.get(0).split("-")[0];
        String fine = orariSelezionati.get(orariSelezionati.size() - 1).split("-")[1];

        prenotazione.setOraInizio(LocalTime.parse(inizio));
        prenotazione.setOraFine(LocalTime.parse(fine));
        prenotazione.setCampo(campo);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
        prenotazione.setCliente(user);
        user.addPrenotazione(prenotazione);

        prenotazioneService.save(prenotazione);

        return "redirect:/utente/prenotazioni";
    }






@GetMapping("/utente/prenotazioni")
public String getPrenotazioniUtente(Model model) {
	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	User user=credentialsService.getCredentials(userDetails.getUsername()).getUser();
	List<Prenotazione> prenotazioni= prenotazioneService.getPrenotazioneByCliente(user);
	model.addAttribute("prenotazioni",prenotazioni);
	return "prenotazioniUtente.html";
	
}







@GetMapping("/utente/prenotazioni/cancella/{idP}")
public String cancellaPrenotazione(Model model,@PathVariable("idP") Long idP) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
    User currentUser = credentialsService.getCredentials(userDetails.getUsername()).getUser();

    Prenotazione prenotazione = prenotazioneService.getPrenotazioneByid(idP);

    if (prenotazione != null && prenotazione.getCliente().getId().equals(currentUser.getId())) {
        prenotazioneService.deletePrenotazioneById(idP);
    }
    

    model.addAttribute("prenotazioni", prenotazioneService.getPrenotazioneByCliente(currentUser));
    return "prenotazioniUtente.html";
}












}