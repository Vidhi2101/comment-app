package demo.repositories;

import demo.entities.User;
import demo.exceptions.DuplicateUserException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

     Optional<User> findByUserName(String userName);


}
