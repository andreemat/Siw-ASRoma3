package siw.uniroma3.asroma3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.repository.CampoRepository;

@Service
public class CampoService {
	@Autowired
	protected CampoRepository campoRepository;
	
	public Campo getCampo(Long id) {
		return this.campoRepository.findById(id).orElse(null);
	}
	public List<Campo> getAllCampi() {
		return (List<Campo>) this.campoRepository.findAll();
	}
	public void deleteCampo(Long id) {
		this.campoRepository.deleteById(id);
	}
	
	public List<Campo> getAllByAssociazioneESport(Long idA,Long idS) {
		return this.campoRepository.findBySportIdAndAssociazioneId(idS, idA);
	}

	public void saveCampo(Campo campo) {
		this.campoRepository.save(campo);
	}
	
	public void addAssociazioneToCampo(Associazione associazione, Campo campo) {
		this.campoRepository.addAssociazioneToCampo(associazione.getId(), campo.getId());
	}

	public List<Campo> filtraPerSportAndAssociazioneAndNome(Long idS, Long idA,String nome) {
		String lower=null;
		if (nome!=null) {
			lower= nome.toLowerCase();
		}
		return this.campoRepository.findCampiByOptionalFilters(idS,idA, lower);
	}
	public List<Campo> getCampiBySport(Long idA,Long sportIdFiltro) {
		return this.campoRepository.findBySportIdAndAssociazioneId(sportIdFiltro,idA);

	}
	
	public boolean existsCampoByNomeAndAssociaizone(String nome, Long idA) {
		return this.campoRepository.existsByNomeAndAssociazioneId(nome, idA);
	}
	

}
