package siw.uniroma3.asroma3.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Citta {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	private String sigla;
	
	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@OneToMany(mappedBy="citta")
	private List<Associazione> associazione;
	
	
	public Long getId() {
		return id;
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
		Citta other = (Citta) obj;
		return Objects.equals(nome, other.nome);
	}

	/**
	 * @return the associazione
	 */
	public List<Associazione> getAssociazione() {
		return associazione;
	}

	/**
	 * @param associazione the associazione to set
	 */
	public void setAssociazione(List<Associazione> associazione) {
		this.associazione = associazione;
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

	public void addAssociazione(@Valid Associazione associazione2) {
		this.associazione.add(associazione2);
		
	}
	
}
