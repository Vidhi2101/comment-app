package demo.repositories;

import demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

     User save(User user);

     Optional<User> findByUserName(String userName);


}
