package demo.services;


import demo.entities.Comment;
import demo.entities.Post;
import demo.entities.User;
import demo.exceptions.PostNotFoundException;
import demo.exceptions.UserNotFoundException;
import demo.repositories.CommentRepository;
import demo.repositories.PostRepository;
import demo.repositories.UserRepository;
import demo.requests.CreateCommentRequest;
import demo.response.CommentResponse;
import demo.response.GetCommentResponse;
import demo.response.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements  CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;


    @Override
    public Comment createComment(CreateCommentRequest createCommentRequest){
        User user = userRepository.findById(createCommentRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(createCommentRequest.getPostId()).orElseThrow(() -> new PostNotFoundException(createCommentRequest.getPostId().toString()));
        return commentRepository.save(createCommentRequest.toComment(createCommentRequest.getMetaData(), post, createCommentRequest.getParentCommentId(), user));
    }


    @Override
    public GetCommentResponse getCommentByPostIdAndParentId(int pageNo, int pageSize, String sortBy, String sortDir, UUID parentCommentId, UUID postId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> comments = commentRepository.findByParentIdAndPostId(pageable, parentCommentId, postId);

        List<CommentResponse> commentList = comments.getContent().stream().map(commentMapper::mapToResponse).collect(Collectors.toList());

        return GetCommentResponse.builder()
                .comments(commentList)
                .pageNo(comments.getNumber())
                .pageSize(comments.getSize())
                .totalElements(comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .last(comments.isLast()).build();
    }

}
