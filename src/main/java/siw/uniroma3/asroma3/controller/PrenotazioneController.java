package siw.uniroma3.asroma3.controller;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

import java.security.Principal;

import java.util.List;
import java.util.stream.Collectors;

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
import siw.uniroma3.asroma3.model.Sport;
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
	public String getPrenotazioniUtente(
	    @ModelAttribute("userDetails") UserDetails userDetails,
	    @RequestParam(required = false) Long associazioneId,
	    @RequestParam(required = false) Long sportId,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFiltro,
	    Model model
	) {
	    User utente = credentialsService.getCredentials(userDetails.getUsername()).getUser();

	    // ==========================================================================================
	    // PARTE 1: OTTENERE I DATI DA VISUALIZZARE (LA LISTA DELLE PRENOTAZIONI FILTRATE)
	    // ==========================================================================================
	    // Questo metodo dovrebbe essere nel tuo PrenotazioneService e usare una query che accetta
	    // tutti i filtri (utente, associazione, sport, data).
	    List<Prenotazione> prenotazioniFiltrate = prenotazioneService.getPrenotazioniFiltratePerUtente(
	        utente, associazioneId, dataFiltro, sportId);

	    // Dividi in future e passate per la visualizzazione
	    List<Prenotazione> future = prenotazioniFiltrate.stream()
	        .filter(p -> !p.getData().isBefore(LocalDate.now()))
	        .toList();
	    List<Prenotazione> passate = prenotazioniFiltrate.stream()
	        .filter(p -> p.getData().isBefore(LocalDate.now()))
	        .toList();

	    // ==========================================================================================
	    // PARTE 2: PREPARARE I DATI PER LE DROPDOWN DEI FILTRI
	    // ==========================================================================================
	    
	    // Per popolare i filtri, abbiamo bisogno di TUTTE le prenotazioni dell'utente, non solo quelle già filtrate.
	    List<Prenotazione> tutteLePrenotazioniDellUtente = prenotazioneService.getPrenotazioneByCliente(utente); // Nuovo metodo da creare in PrenotazioneService

	    // A. Popola il filtro ASSOCIAZIONI
	    List<Long> idAssociazioniCoinvolte = tutteLePrenotazioniDellUtente.stream()
	        .map(p -> p.getCampo().getAssociazione().getId())
	        .distinct()
	        .collect(Collectors.toList());
	    
	    List<Associazione> associazioniPerFiltro = idAssociazioniCoinvolte.isEmpty() ? 
	        new ArrayList<>() : associazioneService.findByIdIn(idAssociazioniCoinvolte);

	    // B. Popola il filtro SPORT (con la logica corretta)
	    List<Prenotazione> prenotazioniDaConsiderarePerSport;

	    if (associazioneId != null) {
	        // CASO B: Un'associazione è già stata selezionata.
	        // Filtriamo le prenotazioni dell'utente solo per quell'associazione.
	        prenotazioniDaConsiderarePerSport = tutteLePrenotazioniDellUtente.stream()
	            .filter(p -> p.getCampo().getAssociazione().getId().equals(associazioneId))
	            .toList();
	    } else {
	        // CASO A: Nessuna associazione selezionata.
	        // Consideriamo tutte le prenotazioni dell'utente.
	        prenotazioniDaConsiderarePerSport = tutteLePrenotazioniDellUtente;
	    }

	    // Ora, da queste prenotazioni, estraiamo gli ID dei campi e poi troviamo gli sport
	    List<Long> idCampiCoinvoltiPerSport = prenotazioniDaConsiderarePerSport.stream()
	        .map(p -> p.getCampo().getId())
	        .distinct()
	        .collect(Collectors.toList());

	    List<Sport> sportPerFiltro = new ArrayList<>();
	    if (!idCampiCoinvoltiPerSport.isEmpty()) {
	        // Usiamo il nuovo metodo del repository che è più pulito
	        sportPerFiltro = sportService.findDistinctSportsByCampoIds(idCampiCoinvoltiPerSport);
	    }
	    
	    // ==========================================================================================
	    // PARTE 3: PASSA TUTTO AL MODELLO
	    // ==========================================================================================
	    model.addAttribute("prenotazioniFuture", future);
	    model.addAttribute("prenotazioniPassate", passate);
	    
	    // Dati per le dropdown dei filtri
	    model.addAttribute("associazioni", associazioniPerFiltro);
	    model.addAttribute("sports", sportPerFiltro);

	    // Valori dei filtri attualmente attivi (per far "ricordare" al form le selezioni)
	    model.addAttribute("associazioneIdFiltro", associazioneId);
	    model.addAttribute("sportIdFiltro", sportId);
	    model.addAttribute("dataFiltro", dataFiltro);

	    return "prenotazioniUtente"; 


	}







	@GetMapping("/utente/prenotazioni/cancella/{idP}")
	public String cancellaPrenotazione(
	        @PathVariable("idP") Long idP,
	        @RequestParam(required = false) Long associazioneId,
	        @RequestParam(required = false) Long campoId,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFiltro
	) {
	    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    User currentUser = credentialsService.getCredentials(userDetails.getUsername()).getUser();
	    Prenotazione prenotazione = prenotazioneService.getPrenotazioneByid(idP);

	    if (prenotazione != null && prenotazione.getCliente().getId().equals(currentUser.getId())) {
	        prenotazioneService.deletePrenotazioneById(idP);
	    }

	    // 🔁 Reindirizza alla pagina prenotazioni, mantenendo i filtri
	    String redirectUrl = String.format(
	        "redirect:/utente/prenotazioni?associazioneId=%s&campoId=%s&dataFiltro=%s",
	        associazioneId != null ? associazioneId : "",
	        campoId != null ? campoId : "",
	        dataFiltro != null ? dataFiltro.toString() : ""
	    );

	    return redirectUrl;
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