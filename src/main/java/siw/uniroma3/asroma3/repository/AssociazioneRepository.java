package siw.uniroma3.asroma3.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import siw.uniroma3.asroma3.model.Associazione;

public interface AssociazioneRepository extends CrudRepository<Associazione,Long> {
	@Transactional
	@Modifying
	@Query(value="UPDATE associazione SET user_id = :userId WHERE id= :associazioneId", nativeQuery = true )
	public void addAdminAssociazione(@Param("associazioneId") Long associazioneId, @Param("userId") Long userId);

	public boolean existsByNomeAndPartitaIVA(String nome, String partitaIVA);

	

}
