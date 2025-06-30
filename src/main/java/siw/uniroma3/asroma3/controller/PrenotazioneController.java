package siw.uniroma3.asroma3.controller;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import siw.uniroma3.asroma3.model.Associazione;
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
	public String selezionaGiorno(@Valid @ModelAttribute("prenotazione") Prenotazione prenotazione, BindingResult bindingResult,
			@PathVariable("idC") Long idC,
			Model model) {
		Campo campo = campoService.getCampo(idC);

			// VALIDAZIONE 1: Non puoi prenotare giorni passati
			if (bindingResult.hasErrors()) {
			
				model.addAttribute("campo", campo);
				model.addAttribute("associazione", campo.getAssociazione());
				model.addAttribute("sport", campo.getSport());
				model.addAttribute("prenotazione", prenotazione);
				return "formDataPrenotazione.html";
			}

			if (prenotazione.getData() != null) {
				DayOfWeek giornoSelezionato = prenotazione.getData().getDayOfWeek();

				if (campo.getGiorniDisponibili() == null || 
						!campo.getGiorniDisponibili().contains(giornoSelezionato)) {

					// Aggiungi messaggio di errore
					model.addAttribute("erroreGiorno", "Il campo è chiuso il " + giornoSelezionato);
					model.addAttribute("campo", campo);
					model.addAttribute("associazione", campo.getAssociazione());
					model.addAttribute("sport", campo.getSport());
					model.addAttribute("prenotazione", prenotazione);

					// Torna al form di selezione data
					return "formDataPrenotazione.html";
				}
			}
		
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
			@RequestParam("orariSelezionati") List<String> orariSelezionati,Model model) {

		Campo campo = campoService.getCampo(idC);

		if (!prenotazioneService.sonoSlotConsecutivi(orariSelezionati, campo.getDurataSlot())) {
			model.addAttribute("erroreSlot", "Gli slot devono essere continui");
			model.addAttribute("campo", campo);
			model.addAttribute("associazione", campo.getAssociazione());
			model.addAttribute("sport", campo.getSport());
			model.addAttribute("prenotazione", prenotazione);
			model.addAttribute("slots", prenotazioneService.getSlot(campo, prenotazione.getData()));
			return "formOrariPrenotazione.html";
		}

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



	@GetMapping("/admin/associazione/{idA}/prenotazioni/{idP}")
	public String visualizzaDettaglioPrenotazione(@PathVariable("idA")Long idA,@PathVariable("idP") Long idP,Model model) {
		model.addAttribute("prenotazione",prenotazioneService.getPrenotazioneByid(idP));
		model.addAttribute("associazione", associazioneService.getAssociazione(idA));
		return "admin/DettaglioPrenotazioneAdmin.html";



	}


	@GetMapping("/admin/associazione/{idA}/prenotazioni")
	public String visualizzaPrenotazioni(@PathVariable("idA") Long idA,@RequestParam(name = "dataFiltro", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFiltro,
			@RequestParam(name = "campoIdFiltro", required = false) Long campoIdFiltro,   @RequestParam(name = "sportIdFiltro", required = false) Long sportIdFiltro, Model model) {
		Associazione associazione = associazioneService.getAssociazione(idA);
		if (associazione == null) {
			return "redirect:/errore";
		}

		List<Prenotazione> prenotazioni = prenotazioneService.getAllPrenotazioneByAssociazione(idA, dataFiltro,campoIdFiltro, sportIdFiltro);


		model.addAttribute("associazione", associazione);
		model.addAttribute("prenotazioni", prenotazioni);
		model.addAttribute("sportDellAssociazione", associazione.getSportList());
		model.addAttribute("campiDellAssociazione", sportIdFiltro != null
				? this.campoService.getCampiBySport(idA,sportIdFiltro)
						: null);

		// Sempre settare tutti i filtri, anche se null
		model.addAttribute("dataFiltroAttuale", dataFiltro);
		model.addAttribute("sportIdFiltroAttuale", sportIdFiltro);
		model.addAttribute("campoIdFiltroAttuale", campoIdFiltro);
		return "admin/prenotazioniAssociazione.html"; 
	}


	@GetMapping("/admin/associazione/{idA}/prenotazioni/cancella/{idP}")
	public String cancellaPrenotazioneAdmin(Model model,@PathVariable("idP") Long idP,@PathVariable("idA") Long idA) {
		Prenotazione prenotazione = prenotazioneService.getPrenotazioneByid(idP);
		prenotazioneService.deletePrenotazioneById(idP);
		model.addAttribute("associazione",this.associazioneService.getAssociazione(idA));
		return "redirect:/admin/associazione/{idA}/prenotazioni";

	}


}