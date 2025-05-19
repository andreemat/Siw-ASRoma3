package siw.uniroma3.asroma3.service;
import siw.uniroma3.asroma3.model.Cliente;
import siw.uniroma3.asroma3.repository.ClienteRepisitory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepisitory clienteRepository;
	
	public Cliente getClienteByid(Long id) {
		return this.clienteRepository.findById(id).orElse(null);
	}
	
	public List<Cliente> getAllClienti(){
		return (List<Cliente>)this.clienteRepository.findAll();
	}
	
	public void deleteClienteById (Long id) {
		this.clienteRepository.deleteById(id);
	}
}
