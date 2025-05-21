package siw.uniroma3.asroma3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.repository.AssociazioneRepository;


@Service
public class AssociazioneService {
	@Autowired AssociazioneRepository associazioneRepository;

	public Associazione getPrenotazineByid(Long id) {
		return this.associazioneRepository.findById(id).orElse(null);
	}
	
	public List<Associazione> getAllPrenotazioni(){
		return (List<Associazione>)this.associazioneRepository.findAll();
	}
	
	public void deletePrenotazioneById (Long id) {
		this.associazioneRepository.deleteById(id);
	}

}
