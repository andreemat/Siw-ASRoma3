package siw.uniroma3.asroma3.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Campo {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String nome;
	private Integer capienza;
	
	@ManyToOne
	private Sport sport;
	
	@ManyToOne
	private Associazione associazione;
	
	@OneToMany(mappedBy="campo")
	private List<Prenotazione> prenotazioni;
	
	public Long getId() {
		return id;
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
	@Override
	public int hashCode() {
		return Objects.hash(capienza, nome, sport);
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
		return Objects.equals(capienza, other.capienza) && Objects.equals(nome, other.nome)
				&& Objects.equals(sport, other.sport);
	}
	
}

