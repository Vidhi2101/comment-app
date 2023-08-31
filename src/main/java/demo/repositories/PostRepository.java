package demo.repositories;

import demo.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

     Post save(Post post);

    List<Post> findAllByUserId(Integer userId);

    Page<Post> findAllByUserId(Pageable pageable, Integer userId);
}
