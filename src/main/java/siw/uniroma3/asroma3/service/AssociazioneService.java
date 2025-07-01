package siw.uniroma3.asroma3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.model.Citta;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.repository.AssociazioneRepository;


@Service
public class AssociazioneService {
	@Autowired AssociazioneRepository associazioneRepository;

	@Transactional
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
	public void addAdminAssociazione(Associazione associazione, User user) {
		this.associazioneRepository.addAdminAssociazione(associazione.getId(), user.getId());
	}

	public boolean existsByNomeAndPartitaIVA(String nome, String partitaIVA) {
		
		return this.associazioneRepository.existsByNomeAndPartitaIVA(nome,partitaIVA);
	}

	public List<Associazione> findByIdIn(List<Long> ids) {
		return (List<Associazione>) this.associazioneRepository.findAllById(ids);
	}

	public List<Associazione> findAssociazioneByCitta(Citta citta) {
	return this.associazioneRepository.findByCittaId(citta.getId()).orElse(null);
		
	}

	

	

}
