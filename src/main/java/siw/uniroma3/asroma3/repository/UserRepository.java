package siw.uniroma3.asroma3.repository;
import org.springframework.data.repository.CrudRepository;
import siw.uniroma3.asroma3.model.User;
public interface UserRepository extends CrudRepository<User,Long> {
	User findByName(String nome);

}
