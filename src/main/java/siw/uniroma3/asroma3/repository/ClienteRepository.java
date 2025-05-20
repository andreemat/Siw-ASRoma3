package siw.uniroma3.asroma3.repository;
import org.springframework.data.repository.CrudRepository;
import siw.uniroma3.asroma3.model.User;
public interface ClienteRepository extends CrudRepository<User,Long> {

}
