package siw.uniroma3.asroma3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Sport;
import siw.uniroma3.asroma3.repository.SportRepository;

@Service
public class SportService {
	@Autowired SportRepository sportRepository;
	
	public Sport getSportByid(Long id) {
		return this.sportRepository.findById(id).orElse(null);
	}
	public List<Sport> getAllSport(){
		return (List<Sport>)this.sportRepository.findAll();
	}
	
	public void deleteSportById (Long id) {
		this.sportRepository.deleteById(id);
	}
	
	public void saveSport(Sport sport) {
		this.sportRepository.save(sport);
	}
	public List<Sport> findByAssociazioneIdAndIdIn(Long associazioneId, List<Long> idSport) {
		
		return this.sportRepository.findByAssociazioneIdAndSportIds(associazioneId, idSport);
	}
	public List<Sport> findDistinctSportsByCampoIds(List<Long> idCampiCoinvoltiPerSport) {
	
		return  this.sportRepository.findDistinctSportsByCampoIds(idCampiCoinvoltiPerSport);
	}
	public Sport findSportByNome(String sport) {
			return this.sportRepository.findByNome(sport);
		
	}
	

	
}
