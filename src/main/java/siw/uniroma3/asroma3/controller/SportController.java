package siw.uniroma3.asroma3.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.repository.CampoRepository;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.CredentialsService;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class SportController {
	
	@Autowired 
	private SportService sportService;
	
	@Autowired
	private CampoService campoService;
	
	@Autowired
	private AssociazioneService associazioneService;

	@Autowired private CredentialsService credentialsService;
	
	
	@GetMapping("/admin/associazione/{idA}/sports")
	public String sports(Model model,@PathVariable("idA") Long id) {
		Associazione associazione = this.associazioneService.getAssociazione(id);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		if(associazione!=null&&user.equals(associazione.getAdminAssociazione())) {
		List<Sport> sportAssociazione = associazione.getSportList();
		
		List<Sport> allSport = new ArrayList<>(this.sportService.getAllSport());
		allSport.removeAll(sportAssociazione);
		List<Sport> altriSport=allSport;
		model.addAttribute("associazione",associazione);
		model.addAttribute("sports",sportAssociazione);
		model.addAttribute("altriSport",altriSport);
		
		return "admin/updateSport.html";}
		return "redirect:/";
	}
	
	
	@GetMapping("/admin/associazione/{idA}/sports/aggiungi/{idS}")
	public String addSports(Model model,@PathVariable("idA") Long id,@PathVariable("idS") Long idS) {
		Associazione associazione = this.associazioneService.getAssociazione(id);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		if(associazione!=null&&user.equals(associazione.getAdminAssociazione())) {
		Sport sport = this.sportService.getSportByid(idS);
		if (!associazione.getSportList().contains(sport)) {
		    associazione.getSportList().add(sport);
		}
		this.associazioneService.saveAssociazione(associazione);
		
		return "redirect:/admin/associazione/{idA}/sports";}
		return "redirect:/";
	}
	
	
	@GetMapping("/admin/associazione/{idA}/sports/rimuovi/{idS}")
	public String removeSport(Model model,@PathVariable("idA") Long idA,@PathVariable("idS") Long idS, RedirectAttributes redirectAttributes) {
		Associazione associazione = this.associazioneService.getAssociazione(idA);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = credentialsService.getCredentials(userDetails.getUsername()).getUser();
		Sport sport = this.sportService.getSportByid(idS);
		if(associazione!=null&&user.equals(associazione.getAdminAssociazione())) {
			if (campoService.existsCampoBySportIdAndAssociazioneId(idS,idA)) {
				redirectAttributes.addFlashAttribute("errorMessage", "Lo sport ha dei campi associati, non puoi rimuoverlo");
				return "redirect:/admin/associazione/{idA}/sports";
			}
			if (associazione.getSportList().contains(sport)) {
			    associazione.getSportList().remove(sport);
			}
			this.associazioneService.saveAssociazione(associazione);
			
			return "redirect:/admin/associazione/{idA}/sports";
			
		}
		
	return "redirect:/";
}
	}
