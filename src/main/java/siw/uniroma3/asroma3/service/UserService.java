package siw.uniroma3.asroma3.service;
import siw.uniroma3.asroma3.model.User;
import siw.uniroma3.asroma3.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository clienteRepository;
	
	public User getClienteByid(Long id) {
		return this.clienteRepository.findById(id).orElse(null);
	}
	
	public List<User> getAllClienti(){
		return (List<User>)this.clienteRepository.findAll();
	}
	
	public void deleteClienteById (Long id) {
		this.clienteRepository.deleteById(id);
	}
}
