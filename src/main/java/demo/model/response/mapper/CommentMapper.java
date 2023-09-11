package demo.model.response.mapper;

import demo.entities.Comment;
import demo.entities.Vote;
import demo.entities.VoteType;
import demo.model.response.CommentResponse;
import demo.repositories.VoteRepository;
import demo.model.response.GetCommentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final VoteRepository voteRepository;
    public static final String SUCCESS = "Comment added successfully";


    public GetCommentResponse mapToResponse(Comment comment){
        return  GetCommentResponse.builder()
                .data(comment.getMetadata())
                .commentId(comment.getId().toString())
                .userId(String.valueOf(comment.getUser().getId()))
                .postId(comment.getPost().getId().toString())
                .likeCount(countVote(comment, VoteType.LIKE.voteType))
                .dislikeCount(countVote(comment,VoteType.DISLIKE.voteType))
                .userName(comment.getUser().getUserName())
                .createdAt(comment.getCreatedAt())
                .build();


    }

    public CommentResponse mapToCreateResponse(Comment comment){
        return  CommentResponse.builder()
                .data(comment.getMetadata())
                .commentId(comment.getId().toString())
                .postId(comment.getPost().getId().toString())
                .userName(comment.getUser().getUserName())
                .createdAt(comment.getCreatedAt())
                .userId(String.valueOf(comment.getUser().getId()))
                .message(SUCCESS)
                .build();


    }

    private Long countVote(Comment comment, Integer voteType) {
        List<Vote> byAttributeId = voteRepository.findByAttributeId(comment.getId());
        return byAttributeId.stream().filter(e -> voteType.equals(e.getVoteType())).count();
    }

}
