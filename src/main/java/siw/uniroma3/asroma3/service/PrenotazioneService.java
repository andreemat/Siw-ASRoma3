package siw.uniroma3.asroma3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	@Autowired PrenotazioneRepository prenotazioneRepository;

	public Prenotazione getPrenotazioneByid(Long id) {
		return this.prenotazioneRepository.findById(id).orElse(null);
	}
	
	public List<Prenotazione> getAllPrenotazioni(){
		return (List<Prenotazione>)this.prenotazioneRepository.findAll();
	}
	
	public void deletePrenotazioneById (Long id) {
		this.prenotazioneRepository.deleteById(id);
	}
	
	
}
