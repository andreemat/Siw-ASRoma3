package siw.uniroma3.asroma3.controller;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


	        case "campo":
	            return campoService.getCampo(id).getImmagine();

	        default:
	            return null;
	    }
	}
	
	@GetMapping("/images/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> getStaticImage(@PathVariable String fileName) {
		try {
			// Carica l'immagine dalle risorse statiche
			Resource resource = new ClassPathResource("static/images/" + fileName);
			
			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}
			
			// Determina il content type basato sull'estensione
			String contentType = determineContentType(fileName);
			
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(contentType))
					.body(resource);
					
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	// Metodo helper per determinare il content type
	private String determineContentType(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
		switch (extension) {
		case "jpg": 
			return "image/jpg";
		case "jpeg":
			return "image/jpeg";
		case "png":
			return "image/png";
		case "gif":
			return "image/gif";
		case "webp":
			return "image/webp";
		case "svg":
			return "image/svg+xml";
		default:
			return "application/octet-stream";
		}
	}
}






