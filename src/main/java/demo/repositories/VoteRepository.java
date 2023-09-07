package demo.repositories;

import demo.entities.Post;
import demo.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {

    List<Vote> findByAttributeId(UUID attributeId);

}
