package siw.uniroma3.asroma3.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import siw.uniroma3.asroma3.model.Campo;
import siw.uniroma3.asroma3.model.Prenotazione;
import siw.uniroma3.asroma3.model.SlotDisponibili;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	@Autowired PrenotazioneRepository prenotazioneRepository;

	public Prenotazione getPrenotazioneByid(Long id) {
		return this.prenotazioneRepository.findById(id).orElse(null);
	}

	public List<Prenotazione> getAllPrenotazioni(){
		return (List<Prenotazione>)this.prenotazioneRepository.findAll();
	}
	public List<SlotDisponibili> getSlot(Campo campo, LocalDate data){
		List<Prenotazione> prenotazioni = prenotazioneRepository.findByDataAndCampo(data, campo);
		List<SlotDisponibili> slots = new LinkedList<>();
		Long durata =campo.getDurataSlot();
		LocalTime orarioSlot = campo.getOraApertura();
		while (orarioSlot.isBefore(campo.getOraChiusura()) ) {
			

			SlotDisponibili slot = new SlotDisponibili((LocalTime.of(orarioSlot.getHour(),orarioSlot.getMinute(),0,0)),orarioSlot.plusMinutes(durata),true);
			if (prenotazioni.stream().anyMatch(prenotazione -> {return slot.getOraInizio().isBefore(prenotazione.getOraFine()) &&
				       slot.getOraFine().isAfter(prenotazione.getOraInizio());}))
				slot.setDisponibile(false);
			orarioSlot =orarioSlot.plusMinutes(campo.getDurataSlot());
			slots.add(slot);
			
		}

		return slots;
	}

	public void deletePrenotazioneById (Long id) {
		this.prenotazioneRepository.deleteById(id);
	}
	
	public List<Prenotazione> getPrenotazioneByCliente(User cliente){
		return this.prenotazioneRepository.findByCliente(cliente);
	}
	@Transactional
	public void save(Prenotazione prenotazione) {
		this.prenotazioneRepository.save(prenotazione);
		
	}
	
	public List<Prenotazione> getAllPrenotazioneByAssociazione(Long idAssociazione,LocalDate data,Long idCampo,Long idSport){
		return this.prenotazioneRepository.findPrenotazioniFiltratePerAssociazione(idAssociazione, data,idCampo,idSport);
	}


	


}
