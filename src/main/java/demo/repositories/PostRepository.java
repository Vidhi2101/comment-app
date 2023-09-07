package demo.repositories;

import demo.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

     Post save(Post post);

    List<Post> findAllByUserId(UUID userId);

    Page<Post> findAllByUserId(Pageable pageable, UUID userId);
}
