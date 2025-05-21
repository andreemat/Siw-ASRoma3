package siw.uniroma3.asroma3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.asroma3.model.Campo;

public interface CampoRepository extends CrudRepository<Campo, Long> {
	
	public List<Campo> findBySportIdAndAssociazioneId(Long SportId,Long AssociazioneId);
	
}
