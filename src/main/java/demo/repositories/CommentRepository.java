package demo.repositories;


import demo.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

        /**
         * @param parentId
         * @param postId
         */
         List<Comment> findByParentIdAndPostId(UUID parentId, UUID postId);

         Page<Comment> findByParentIdAndPostId(Pageable pageable, UUID parentId, UUID postId);

         Comment save(Comment comment);

         List<Comment> findByPostId(UUID postId);

        Optional<Comment> findTopByParentIdAndPostIdOrderByCreatedAtDesc(UUID parentId, UUID postId);


}
