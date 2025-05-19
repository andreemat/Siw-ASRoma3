package siw.uniroma3.asroma3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.repository.SportRepository;

@Service
public class SportService {
	@Autowired SportRepository sportRepository;
	
	public Sport getPrenotazineByid(Long id) {
		return this.sportRepository.findById(id).orElse(null);
	}
	public List<Sport> getAllPrenotazioni(){
		return (List<Sport>)this.sportRepository.findAll();
	}
	
	public void deletePrenotazioneById (Long id) {
		this.sportRepository.deleteById(id);
	}
}
