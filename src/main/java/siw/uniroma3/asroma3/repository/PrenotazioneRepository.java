package siw.uniroma3.asroma3.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

	List<Prenotazione> findByDataAndCampo(LocalDate data, Campo campo);
	List<Prenotazione> findByCliente(User cliente);
	
}
