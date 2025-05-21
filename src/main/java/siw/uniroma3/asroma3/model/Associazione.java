package siw.uniroma3.asroma3.model;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Associazione {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nome;
	private String indirizzo;
	
	
	
	@ManyToMany
    private List<Sport> sportList;
	
	@OneToMany(mappedBy="associazione")
    private List<Campo> campi;

	public Associazione() {
		this.sportList= new LinkedList<Sport>();
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


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}



	public List<Sport> getSportList() {
		return sportList;
	}
	public void setSportList(List<Sport> sportList) {
		this.sportList = sportList;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, indirizzo, nome);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Associazione other = (Associazione) obj;
		return Objects.equals(id, other.id) && Objects.equals(indirizzo, other.indirizzo)
				&& Objects.equals(nome, other.nome);
	}
	
	
	
}

