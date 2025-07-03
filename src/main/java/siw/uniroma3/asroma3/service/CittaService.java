package siw.uniroma3.asroma3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.asroma3.model.Citta;
import siw.uniroma3.asroma3.repository.CittaRepository;

@Service
public class CittaService {
	@Autowired CittaRepository cittaReposiory;
	
	public List<Citta> findAll(){
		return (List<Citta>) this.cittaReposiory.findAll();
	}

	public void save(Citta citta) {
		this.cittaReposiory.save(citta);
		
	}

	public Citta findCittaByNome(String searchCittaNome) {
		return this.cittaReposiory.findByNome(searchCittaNome);
	}

}
