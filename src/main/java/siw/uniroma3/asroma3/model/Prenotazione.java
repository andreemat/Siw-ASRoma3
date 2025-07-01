
package siw.uniroma3.asroma3.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import jakarta.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Prenotazione {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	private LocalDate data;
	private LocalTime oraInizio;
	private LocalTime oraFine;
	
	@ManyToOne
	private Campo campo;
	
	@ManyToOne
	private User cliente;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
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
	
	public User getCliente() {
		return cliente;
	}
	public void setCliente(User cliente) {
		this.cliente = cliente;
	}
	
	public Campo getCampo() {
		return campo;
	}
	public void setCampo(Campo campo) {
		this.campo = campo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(campo, data, oraFine, oraInizio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prenotazione other = (Prenotazione) obj;
		return Objects.equals(campo, other.campo) && Objects.equals(data, other.data)
				&& Objects.equals(oraFine, other.oraFine) && Objects.equals(oraInizio, other.oraInizio);
	}
	
	
	
	

}

