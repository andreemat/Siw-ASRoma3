package siw.uniroma3.asroma3.model;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Associazione {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String indirizzo;
	
	private Integer annoFondazione;
	@NotBlank
	private String partitaIVA; 
	@Column(length = 1000)
	@Size(max = 1000)
	private String descrizione;
	@NotBlank
	@Pattern(regexp = "^(\\+39)?\\s?3[1-9][0-9]\\d{6,7}$",message = "{telefono.invalid}")
	private String telefono;
	@NotBlank
	@Email
	private String email;
	@Column(columnDefinition = "bytea",nullable=true)
	private byte[] immagine; //path
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	User adminAssociazione;
	
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
	
	
	
	public byte[] getImmagine() {
		return immagine;
	}
	public void setImmagine(byte[] immagine) {
		this.immagine = immagine;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, indirizzo, nome);
	}


	public User getAdminAssociazione() {
		return adminAssociazione;
	}
	public void setAdminAssociazione(User adminAssociazione) {
		this.adminAssociazione = adminAssociazione;
	}
	public List<Campo> getCampi() {
		return campi;
	}
	/**
	 * @return the annoFondazione
	 */
	public Integer getAnnoFondazione() {
		return annoFondazione;
	}
	/**
	 * @param annoFondazione the annoFondazione to set
	 */
	public void setAnnoFondazione(Integer annoFondazione) {
		this.annoFondazione = annoFondazione;
	}
	/**
	 * @return the partitaIVA
	 */
	public String getPartitaIVA() {
		return partitaIVA;
	}
	/**
	 * @param partitaIVA the partitaIVA to set
	 */
	public void setPartitaIVA(String partitaIVA) {
		this.partitaIVA = partitaIVA;
	}
	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}
	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	public void setCampi(List<Campo> campi) {
		this.campi = campi;
	}
	
	public void addSport(Sport sport) {
		if (!this.sportList.contains(sport))
			this.sportList.add(sport);
	}
	public void addCampo(Campo campo) {
		if (!this.campi.contains(campo))
			this.campi.add(campo);
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

