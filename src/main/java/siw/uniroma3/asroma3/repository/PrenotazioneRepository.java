package siw.uniroma3.asroma3.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

	List<Prenotazione> findByDataAndCampo(LocalDate data, Campo campo);
	List<Prenotazione> findByCliente(User cliente);
	

@Query("SELECT p FROM Prenotazione p " +
       "WHERE p.campo.associazione.id = :associazioneId " +
       // Aggiungiamo un CAST esplicito per ogni parametro che può essere null
       "AND (CAST(:dataFiltro AS date) IS NULL OR p.data = :dataFiltro) " +
       "AND (CAST(:campoIdFiltro AS long) IS NULL OR p.campo.id = :campoIdFiltro) " +
       "AND (CAST(:sportIdFiltro AS long) IS NULL OR p.campo.sport.id = :sportIdFiltro) " +
       "ORDER BY p.data DESC, p.oraInizio DESC")
     List<Prenotazione> findPrenotazioniFiltratePerAssociazione(
              Long associazioneId,
              LocalDate dataFiltro,
              Long campoIdFiltro,
              Long sportIdFiltro
     );

@Query("SELECT p FROM Prenotazione p " +
	       "WHERE p.cliente = :utente " +
	       "AND (CAST(:dataFiltro AS date) IS NULL OR p.data = :dataFiltro) " +
	       "AND (:campoIdFiltro IS NULL OR p.campo.id = :campoIdFiltro) " +
	       "AND (:associazioneIdFiltro IS NULL OR p.campo.associazione.id = :associazioneIdFiltro) " +
	       "ORDER BY p.data DESC, p.oraInizio DESC")
	List<Prenotazione> findPrenotazioniFiltratePerUtente(
	    User utente,
	    Long associazioneIdFiltro,
	    LocalDate dataFiltro,
	    Long campoIdFiltro
	);

}
