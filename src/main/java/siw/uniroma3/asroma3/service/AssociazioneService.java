package siw.uniroma3.asroma3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.repository.AssociazioneRepository;


@Service
public class AssociazioneService {
	@Autowired AssociazioneRepository associazioneRepository;

	public Associazione getAssociazione(Long id) {
		return this.associazioneRepository.findById(id).orElse(null);
	}
	
	public List<Associazione> getAllAssociazioni(){
		return (List<Associazione>)this.associazioneRepository.findAll();
	}
	
	public void deleteAssociazione (Long id) {
		this.associazioneRepository.deleteById(id);
	}
	public void saveAssociazione(Associazione associazione) {
		this.associazioneRepository.save(associazione);
	}

}
