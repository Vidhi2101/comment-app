package demo.services;


import demo.AppConstants;
import demo.entities.Comment;
import demo.entities.Post;
import demo.entities.User;
import demo.exceptions.BadRequestException;
import demo.exceptions.PostNotFoundException;
import demo.exceptions.UserNotFoundException;
import demo.model.response.CommentResponse;
import demo.repositories.CommentRepository;
import demo.repositories.PostRepository;
import demo.repositories.UserRepository;
import demo.model.request.CreateCommentRequest;
import demo.model.response.GetCommentResponse;
import demo.model.response.GetPaginatedCommentResponse;
import demo.model.response.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public CommentResponse createComment(CreateCommentRequest createCommentRequest){
       validateRequest(createCommentRequest);
        User user = userRepository.findById(convertToUUID(createCommentRequest.getUserId())).orElseThrow(() -> new UserNotFoundException("User not found"));
        Post post = postRepository.findById(convertToUUID(createCommentRequest.getPostId())).orElseThrow(() -> new PostNotFoundException("Post not found"));
       //TODO:// check why it is saving different values than UUID in parentId field.
        return commentMapper.mapToCreateResponse(commentRepository.save(createCommentRequest.toComment(createCommentRequest.getMetaData(), post, convertToUUID(createCommentRequest.getParentCommentId()), user)));
    }


    @Override
    public GetPaginatedCommentResponse getCommentByPostIdAndParentId(int pageNo, int pageSize, String sortDir, String parentCommentId, String postId) {
        //can add a user not found check as well
        Post post = postRepository.findById(convertToUUID(postId)).orElseThrow(() -> new PostNotFoundException("Post not found"));
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(AppConstants.DEFAULT_SORT_BY).ascending()
                : Sort.by(AppConstants.DEFAULT_SORT_BY).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> comments = commentRepository.findByParentIdAndPostId(pageable, convertToUUID(parentCommentId), convertToUUID(postId));

        List<GetCommentResponse> commentList = comments.getContent().stream().map(commentMapper::mapToResponse).collect(Collectors.toList());

        return GetPaginatedCommentResponse.builder()
                .comments(commentList)
                .pageNo(comments.getNumber())
                .pageSize(comments.getSize())
                .totalElements(comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .last(comments.isLast()).build();
    }

    @Override
    public List<GetCommentResponse> getReplies(String commentId, String postId, int replyCount) {
        Post post = postRepository.findById(convertToUUID(postId)).orElseThrow(() -> new PostNotFoundException("Post not found"));
        int count = replyCount;
        List<GetCommentResponse> replyList = new ArrayList<>();
        UUID parentId = convertToUUID(commentId);
        do{
            Optional<Comment> comment = commentRepository.findTopByParentIdAndPostIdOrderByCreatedAtDesc(parentId, convertToUUID(postId));
            if(!comment.isPresent())
                break;

            replyList.add(commentMapper.mapToResponse(comment.get()));
            parentId = comment.get().getId();

            count = count - 1;
        } while(!replyList.isEmpty() || count > 0);

        return replyList;
    }

    private UUID convertToUUID(String id){
        if(id == null) return null;
        try {

            return AppConstants.convertToUUID(id);
        }catch (IllegalArgumentException ex){
            throw new BadRequestException("Parameter is incorrect");
        }
    }
   private void validateRequest(CreateCommentRequest createCommentRequest){
       if(createCommentRequest == null || createCommentRequest.getPostId() == null || createCommentRequest.getUserId() == null)
           throw new BadRequestException("Wrong request");
   }

}
