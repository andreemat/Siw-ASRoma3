package siw.uniroma3.asroma3.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name= "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	@NotBlank
	@Email
	private String email;
	private int eta;
	@OneToMany(mappedBy = "adminAssociazione")
	private List<Associazione> associazioni;
	@OneToMany(mappedBy= "cliente")
	private List<Prenotazione> prenotazioni;
	@NotNull
	@ManyToOne
	Citta citta;
	
	
	@OneToOne(mappedBy="user")
	Credentials credentials;
	
	public User() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String nome) {
		this.name = nome;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String cognome) {
		this.surname = cognome;
	}
	

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	public void addPrenotazione(Prenotazione prenotazione) {
		this.prenotazioni.add(prenotazione);
		prenotazione.setCliente(this);
	}
	
	public List<Associazione> getAssociazioni() {
		return associazioni;
	}
	public void setAssociazioni(List<Associazione> associazioni) {
		this.associazioni = associazioni;
	}
	
	public Citta getCitta() {
		return citta;
	}
	public void setCitta(Citta citta) {
		this.citta = citta;
	}
	public void addAssociazione(Associazione associazione) {
		if (this.associazioni==null)
			this.associazioni= new LinkedList<Associazione> ();
		this.associazioni.add(associazione);
	}
	@Override
	public int hashCode() {
		return Objects.hash(name,surname);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(name, other.name)&& Objects.equals(surname, other.surname);
	}
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}

}
