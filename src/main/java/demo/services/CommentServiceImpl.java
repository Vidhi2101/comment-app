package demo.services;


import demo.entities.Comment;
import demo.entities.Post;
import demo.exceptions.PostNotFoundException;
import demo.repositories.CommentRepository;
import demo.repositories.PostRepository;
import demo.requests.CreateCommentRequest;
import demo.response.GetCommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements  CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(CreateCommentRequest createCommentRequest){
        Post post = postRepository.findById(createCommentRequest.getPostId()).orElseThrow(() -> new PostNotFoundException(createCommentRequest.getPostId().toString()));
        return commentRepository.save(createCommentRequest.toComment(createCommentRequest.getMetaData(), post, createCommentRequest.getParentCommentId()));
    }

//    public CompletionStage<List<Comment>> getCommentByPostIdAndParentId(Integer parentCommentId, Integer postId){
//        return CompletableFuture.completedFuture(commentRepository.findByParentIdAndPostId(parentCommentId, postId));
//
//    }

    @Override
    public GetCommentResponse getCommentByPostIdAndParentId(int pageNo, int pageSize, String sortBy, String sortDir, Integer parentCommentId, Integer postId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> comments = commentRepository.findByParentIdAndPostId(pageable, parentCommentId, postId);

        List<Comment> commentList = comments.getContent();

        return GetCommentResponse.builder()
                .comments(commentList)
                .pageNo(comments.getNumber())
                .pageSize(comments.getSize())
                .totalElements(comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .last(comments.isLast()).build();
    }

}
