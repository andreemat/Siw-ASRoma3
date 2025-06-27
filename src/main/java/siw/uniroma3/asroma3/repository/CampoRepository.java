package siw.uniroma3.asroma3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import siw.uniroma3.asroma3.model.Campo;

public interface CampoRepository extends CrudRepository<Campo, Long> {
	
	public List<Campo> findBySportIdAndAssociazioneId(Long SportId,Long AssociazioneId);

	@Transactional
	@Modifying
	@Query(value="UPDATE campo SET associazione_id = :associazioneId WHERE id= :campoId", nativeQuery = true )
	public void addAssociazioneToCampo(@Param("associazioneId") Long associazioneId, @Param("campoId") Long campoId);

	List<Campo> findByAssociazioneIdAndSportId(Long idAssociazione, Long idSport);
}
