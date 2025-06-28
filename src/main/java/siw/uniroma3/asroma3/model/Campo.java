package siw.uniroma3.asroma3.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;         // Importa l'interfaccia Set
import java.util.HashSet;     // Importa un'implementazione comune di Set
import jakarta.validation.Constraint;
// Se stai usando EnumSet (fortemente consigliato per gli enum come DayOfWeek)
import java.util.EnumSet;
import java.time.DayOfWeek;
import java.util.TreeSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
@Entity
public class Campo {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@Positive
	@NotNull
	private Integer durataSlot;
	@Positive
	@NotNull
	private Integer capienza;
	

	@NotNull
	private LocalTime oraApertura;
	@NotNull
	private LocalTime oraChiusura;
	
	private Set<DayOfWeek> giorniDisponibili ;
	
	@Column(columnDefinition = "bytea",nullable=true)
	private byte[] immagine;
	
	@ManyToOne
	private Sport sport;
	
	@ManyToOne
	private Associazione associazione;
	
	@OneToMany(mappedBy="campo")
	private List<Prenotazione> prenotazioni;
	
	public Long getId() {
		return id;
	}
	public Campo() {
		this.id = null;
		
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Sport getSport() {
		return sport;
	}
	public void setSport(Sport sport) {
		this.sport = sport;
	}
	public Integer getCapienza() {
		return capienza;
	}
	public void setCapienza(Integer capienza) {
		this.capienza = capienza;
	}
	
	public LocalTime getOraApertura() {
		return oraApertura;
	}
	public void setOraApertura(LocalTime oraApertura) {
		this.oraApertura = oraApertura;
	}
	public LocalTime getOraChiusura() {
		return oraChiusura;
	}
	public void setOraChiusura(LocalTime oraChiusura) {

		this.oraChiusura = oraChiusura;
	}
	
	public Integer getDurataSlot() {
		return durataSlot;
	}
	public void setDurataSlot(Integer durataSlot) {
		this.durataSlot = durataSlot;
	}
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	public void addGiorno(String giorno) {
		giorniDisponibili.add(DayOfWeek.valueOf(giorno));
	}
	
	
	public Set<DayOfWeek> getGiorniDisponibili() {
		return giorniDisponibili;
	}
	public void setGiorniDisponibili(Set<DayOfWeek> giorniDisponibili) {
		this.giorniDisponibili = giorniDisponibili;
	}
	
	
	
	public byte[] getImmagine() {
		return immagine;
	}
	public void setImmagine(byte[] immagine) {
		this.immagine = immagine;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(associazione, capienza, durataSlot, giorniDisponibili, nome, oraApertura, oraChiusura,
				prenotazioni, sport);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campo other = (Campo) obj;
		return Objects.equals(associazione, other.associazione) && Objects.equals(capienza, other.capienza)
				&& Objects.equals(durataSlot, other.durataSlot)
				&& Objects.equals(giorniDisponibili, other.giorniDisponibili) && Objects.equals(nome, other.nome)
				&& Objects.equals(oraApertura, other.oraApertura) && Objects.equals(oraChiusura, other.oraChiusura)
				&& Objects.equals(prenotazioni, other.prenotazioni) && Objects.equals(sport, other.sport);
				
	}
	public Associazione getAssociazione() {
		return this.associazione;
	}
	public void setAssociazione(Associazione associazione) {
		this.associazione=associazione;
	}
}

