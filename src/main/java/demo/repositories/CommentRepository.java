package demo.repositories;


import demo.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

        /**
         * @param parentId
         * @param postId
         */
         List<Comment> findByParentIdAndPostId(Integer parentId, Integer postId);

         Page<Comment> findByParentIdAndPostId(Pageable pageable, Integer parentId, Integer postId);

         Comment save(Comment comment);

}
