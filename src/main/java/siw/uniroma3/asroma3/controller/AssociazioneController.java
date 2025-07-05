package siw.uniroma3.asroma3.controller;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.model.Citta;
import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.repository.AssociazioneRepository;
import siw.uniroma3.asroma3.repository.UserRepository;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CittaService;
import siw.uniroma3.asroma3.service.CredentialsService;
import siw.uniroma3.asroma3.service.SportService;
import siw.uniroma3.asroma3.service.UserService;
import siw.uniroma3.asroma3.validator.AssociazioneValidator;

@Controller
public class AssociazioneController {
	@Autowired
	private AssociazioneService associazioneService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private UserService userService;
	@Autowired private AssociazioneValidator associazioneValidator;
	@Autowired private MessageSource messageSource;
	@Autowired private CittaService cittaService;
	@Autowired private SportService sportService;
	

	@GetMapping("/associazione/{id}")

	public String mostraAssociazione(@PathVariable("id")Long id, Model model) {
		Associazione associazione = this.associazioneService.getAssociazione(id);

		model.addAttribute("sports", associazione.getSportList());
		model.addAttribute("associazione", associazione);
		return "associazione.html";
	}

	@GetMapping("/admin/registra-associazione")
	public String registraAssociazione(Model model) {
		
		
		model.addAttribute("associazione",new Associazione());
		model.addAttribute("citta",this.cittaService.findAll());
		return "admin/formAssociazione.html";
	}
	

	@PostMapping("/admin/registra-associazione")
	public String registraAssociazione(
			@Valid @ModelAttribute("associazione") Associazione associazione,
			BindingResult bindingResult,
			Model model, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		this.associazioneValidator.validate(associazione,bindingResult);
	       if (bindingResult.hasErrors()) {
	            
	    		model.addAttribute("citta",this.cittaService.findAll());
	            return "admin/formAssociazione.html";
	        } 
		if (file != null && !file.isEmpty()) {
	        // Salva i byte del file dentro l'entità
	        associazione.setImmagine(file.getBytes());
	    }

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		Citta citta = associazione.getCitta();
		associazione.setAdminAssociazione(user);
		citta.addAssociazione(associazione);
		this.associazioneService.addAdminAssociazione(associazione, user);
		Associazione associazioneDatabase = this.associazioneService.getAssociazione(associazione.getId());
		if(associazioneDatabase!=null) {
			associazione.setSportList(associazioneDatabase.getSportList());
			associazione.setCampi(associazioneDatabase.getCampi());
		}
		this.associazioneService.saveAssociazione(associazione);
		this.cittaService.save(citta);
		return "redirect:/";
	}
	
	@GetMapping("/admin/associazione/{idA}")
	public String associazioneAdmin(@PathVariable("idA") Long id,Model model) {
		Associazione associazione = this.associazioneService.getAssociazione(id);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		if(associazione!=null&&user.equals(associazione.getAdminAssociazione())) {
		model.addAttribute("sports", associazione.getSportList());
		model.addAttribute("associazione", associazione);
		return "admin/associazioneAdmin";
		}
		return "redirect:/";
		
		
	}
	
	@GetMapping("admin/modifica/associazione/{idA}")
	public String modificaAssociazione(@PathVariable("idA") Long idA, Model model) {
		 Associazione associazione = this.associazioneService.getAssociazione(idA);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		if(associazione!=null&&user.equals(associazione.getAdminAssociazione())) {
	    model.addAttribute("associazione", associazione);
	    model.addAttribute("citta", this.cittaService.findAll());
	    return "admin/formAssociazione.html";}
		return "redirect:/";
	}
	@GetMapping("/associazioni/catalogo")
	public String mostraCatalogoAssociazioni(
	        Model model,
	        @PageableDefault(page = 0, size = 2, sort = "nome") Pageable pageable,
	        @RequestParam(name="searchNome",required = false) String searchNome,
	        @RequestParam(name="searchCittaNome",required = false) String searchCittaNome,
	        @RequestParam(name="sport",required = false) String sport
	) {
	    Page<Associazione> associazionePage;
	    Long sportId=null;
	    if(sport!=null&&!sport.trim().isEmpty()) {
	    	Sport sportSearch = sportService.findSportByNome(sport);
	    	if (sportSearch !=null)
	    		sportId=sportSearch.getId();
	    	}
	    Long cittaId=null;
	    if(searchCittaNome!=null&&!searchCittaNome.trim().isEmpty()) {
	    	Citta cittaSearch = cittaService.findCittaByNome(searchCittaNome);
	    	if (cittaSearch !=null)
	    		cittaId=cittaSearch.getId();
	    	}
	    
	    
	    associazionePage= this.associazioneService.findByFilters(sportId, cittaId, searchNome, pageable);
	    model.addAttribute("sport", sport);
	    model.addAttribute("searchNome", searchNome);
	    model.addAttribute("searchCittaNome", searchCittaNome);

	    model.addAttribute("associazionePage", associazionePage);
	    model.addAttribute("currentPage", associazionePage.getNumber() + 1);
	    model.addAttribute("totalPages", associazionePage.getTotalPages());
	    model.addAttribute("totalItems", associazionePage.getTotalElements());
	    model.addAttribute("pageSize", pageable.getPageSize());

	    String currentSortParam = "";
	    if (pageable.getSort().isSorted()) {
	        currentSortParam = pageable.getSort().toString()
	                             .replace(": ", ",")
	                             .replace(" ", "")
	                             .toLowerCase();
	    }
	    model.addAttribute("currentSortParam", currentSortParam);

	    return "catalogo.html";
	}









}
