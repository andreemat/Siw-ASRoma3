package siw.uniroma3.asroma3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Sport;

public interface SportRepository extends CrudRepository<Sport, Long> {
	
	@Query("SELECT s FROM sport s JOIN s.associazioni a " +
		       "WHERE s.id IN :sportIds " +
		       "AND (:associazioneId IS NULL OR a.id = :associazioneId)")
		List<Sport> findByAssociazioneIdAndSportIds( // Ho rinominato il metodo per chiarezza, puoi tenere il tuo
		    @Param("associazioneId") Long associazioneId,
		    @Param("sportIds") List<Long> sportIds
		);

	
	@Query("SELECT DISTINCT c.sport FROM Campo c WHERE c.id IN :campoIds ORDER BY c.sport.nome ASC")
    List<Sport> findDistinctSportsByCampoIds(@Param("campoIds") List<Long> campoIds);


	Sport findByNome(String sport);
}
