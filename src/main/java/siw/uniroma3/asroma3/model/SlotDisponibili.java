package siw.uniroma3.asroma3.model;

import java.time.LocalTime;

public class SlotDisponibili {
	private LocalTime oraInizio;
	private LocalTime oraFine;
	private boolean disponibile;
	
	

	public SlotDisponibili(LocalTime oraInizio, LocalTime oraFine, boolean disponibile) {
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.disponibile = disponibile;
	}
	public LocalTime getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}
	public LocalTime getOraFine() {
		return oraFine;
	}
	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}
	public boolean isDisponibile() {
		return disponibile;
	}
	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}
	
	
}
