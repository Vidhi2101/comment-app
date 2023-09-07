package demo.repositories;

import demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

     User save(User user);

     Optional<User> findByUserName(String userName);


}
