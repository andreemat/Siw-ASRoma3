package siw.uniroma3.asroma3.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name="sport")
public class Sport {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nome;
	
	private String immagine;
	
	@OneToMany(mappedBy="sport")
	private List<Campo> campi;
	
	@ManyToMany(mappedBy ="sportList")
	private List<Associazione> associazioni;

	
	public Sport() {
	}

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

	

	public List<Campo> getCampi() {
		return campi;
	}

	public void setCampi(List<Campo> campi) {
		this.campi = campi;
	}

	

	public List<Associazione> getAssociazioni() {
		return associazioni;
	}

	public void setAssociazioni(List<Associazione> associazioni) {
		this.associazioni = associazioni;
	}
	
	

	

	

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sport other = (Sport) obj;
		return Objects.equals(nome, other.nome);
	}
	
	
	
	
	
	
	

}
