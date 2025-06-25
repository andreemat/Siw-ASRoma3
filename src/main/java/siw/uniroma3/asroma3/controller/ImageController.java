package siw.uniroma3.asroma3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.service.AssociazioneService;
import siw.uniroma3.asroma3.service.CampoService;
import siw.uniroma3.asroma3.service.SportService;

@Controller
public class ImageController {

	@Autowired
	private AssociazioneService associazioneService;
	@Autowired
	private SportService sportService;
	@Autowired
	private CampoService campoService;

	@PostMapping("/admin/upload/{type}/{id}")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			@PathVariable("type") String type,
			@PathVariable("id") Long id,
			Model model) {
		if (file.isEmpty()) {
			model.addAttribute("message", "File non valido");
			return "uploadResult";
		}

		try {
			byte[] imageBytes = file.getBytes();


			switch (type.toLowerCase()) {
			case "associazione":
				Associazione ass = associazioneService.getAssociazione(id);
				ass.setImmagine(imageBytes);
				associazioneService.saveAssociazione(ass);
				break;

			case "sport":
				Sport sport = sportService.getSportByid(id);
				sport.setImmagine(imageBytes);
				sportService.saveSport(sport);
				break;

			case "campo":
				Campo campo = campoService.getCampo(id);
				campo.setImmagine(imageBytes);
				campoService.saveCampo(campo);
				break;

			default:
				model.addAttribute("message", "Tipo non valido");
				return "uploadResult";
			}

			model.addAttribute("message", "Immagine salvata nel database!");
		} catch (IOException e) {
			model.addAttribute("message", "Errore durante il caricamento");
		}

		return "redirect:/";
	}



	@GetMapping ("/admin/upload/{type}/{id}")
	public String getFormImage(@PathVariable("type") String type,@PathVariable("id") Long id,Model model) {
		model.addAttribute("type", type);
		model.addAttribute("id", id);
		return "admin/formImage";

	}
	
	@GetMapping("/image/{type}/{id}")
	@ResponseBody
	public byte[] getImage(@PathVariable("type") String type, @PathVariable("id") Long id) {
	    switch (type.toLowerCase()) {
	        case "associazione":
	            return associazioneService.getAssociazione(id).getImmagine();

	        case "sport":
	            return sportService.getSportByid(id).getImmagine();

	        case "campo":
	            return campoService.getCampo(id).getImmagine();

	        default:
	            return null;
	    }
	}





}
