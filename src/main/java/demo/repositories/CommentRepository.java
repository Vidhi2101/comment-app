package demo.repositories;


import demo.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {


    /**
     * @param pageable
     * @param parentId
     * @param postId
     */
    Page<Comment> findByParentIdAndPostId(Pageable pageable, UUID parentId, UUID postId);

         Comment save(Comment comment);

        Optional<Comment> findTopByParentIdAndPostIdOrderByCreatedAtDesc(UUID parentId, UUID postId);


}
