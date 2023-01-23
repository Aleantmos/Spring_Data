package softuni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.entities.Client;

@Repository
public interface UserRepository extends JpaRepository<Client, Long> {

    Client getUserByUsername(String username);

    Client findUserById(long id);
}
