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
	        String uploadDir = "src/main/resources/static/images/" + type;
	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	        Path filepath = Paths.get(uploadDir, filename);
	        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

	        // path relativo per il client
	        String imagePath = "/images/" + type + "/" + filename;

	        switch (type.toLowerCase()) {
	            case "associazione":
	                Associazione ass = associazioneService.getAssociazione(id);
	                ass.setUrlImage(imagePath);
	                associazioneService.saveAssociazione(ass);
	                break;

	            case "sport":
	                Sport sport = sportService.getSportByid(id);
	                sport.setUrlImage(imagePath);
	                sportService.saveSport(sport);
	                break;

	            case "campo":
	                Campo campo = campoService.getCampo(id);
	                campo.setUrlImage(imagePath);
	                campoService.saveCampo(campo);
	                break;

	            default:
	                model.addAttribute("message", "Tipo non valido");
	                return "uploadResult";
	        }

	        model.addAttribute("message", "File caricato con successo!");
	        model.addAttribute("imagePath", imagePath);

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
	
	
	

}
