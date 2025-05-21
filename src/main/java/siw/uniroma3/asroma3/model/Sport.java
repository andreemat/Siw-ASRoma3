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
public class Sport {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy="sport")
	private List<Campo> campi;
	
	@ManyToOne
	private Associazione associazione;

	
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

	public Associazione getAssociazione() {
		return associazione;
	}

	public void setAssociazione(Associazione associazione) {
		this.associazione = associazione;
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
