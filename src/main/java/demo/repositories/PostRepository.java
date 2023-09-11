package demo.repositories;

import demo.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

     Post save(Post post);

    List<Post> findAllByUserId(UUID userId);

//    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    Page<Post> findPostsByUserId(UUID userId, Pageable pageable);
}
