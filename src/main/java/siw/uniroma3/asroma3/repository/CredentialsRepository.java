package siw.uniroma3.asroma3.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.asroma3.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	public Optional<Credentials> findByUsername(String usernam);

}
