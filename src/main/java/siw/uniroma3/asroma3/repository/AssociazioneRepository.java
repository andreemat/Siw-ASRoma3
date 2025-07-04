package siw.uniroma3.asroma3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import siw.uniroma3.asroma3.model.Associazione;
import siw.uniroma3.asroma3.model.Campo;

public interface AssociazioneRepository extends JpaRepository<Associazione,Long> {
	@Transactional
	@Modifying
	@Query(value="UPDATE associazione SET user_id = :userId WHERE id= :associazioneId", nativeQuery = true )
	public void addAdminAssociazione(@Param("associazioneId") Long associazioneId, @Param("userId") Long userId);

	public boolean existsByNomeAndPartitaIVA(String nome, String partitaIVA);

	public Optional<List<Associazione>> findByCittaId(Long id);

	 @Query(value = "SELECT DISTINCT a.* FROM associazione a " +
             "LEFT JOIN associazione_sport_list ass ON a.id = ass.associazioni_id " + // MODIFICATO: da associazione_id a associazioni_id
             "WHERE (:sportId IS NULL OR ass.sport_list_id = :sportId) " + // MODIFICATO: da sport_id a sport_list_id
             "AND (:nome IS NULL OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
             "AND (:cittaId IS NULL OR a.citta_id = :cittaId)",
      countQuery = "SELECT count(DISTINCT a.id) FROM associazione a " +
                   "LEFT JOIN associazione_sport_list ass ON a.id = ass.associazioni_id " + // MODIFICATO
                   "WHERE (:sportId IS NULL OR ass.sport_list_id = :sportId) " + // MODIFICATO
                   "AND (:nome IS NULL OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
                   "AND (:cittaId IS NULL OR a.citta_id = :cittaId)",
      nativeQuery = true)
Page<Associazione> findAssociazioniByOptionalFilters(
       @Param("sportId") Long sportId,
       @Param("nome") String nome,
       @Param("cittaId") Long cittaId,
       Pageable pageable);


}
