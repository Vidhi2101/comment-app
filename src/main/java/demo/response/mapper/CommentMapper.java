package demo.response.mapper;

import demo.entities.Comment;
import demo.entities.Post;
import demo.entities.Vote;
import demo.entities.VoteType;
import demo.repositories.VoteRepository;
import demo.response.CommentResponse;
import demo.response.PostResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final VoteRepository voteRepository;

    public CommentResponse mapToResponse(Comment comment){
        return  CommentResponse.builder()
                .data(comment.getMetadata())
                .id(comment.getId().toString())
                .postId(comment.getPost().getId().toString())
                .likeCount(countVote(comment, VoteType.LIKE.voteType))
                .dislikeCount(countVote(comment,VoteType.DISLIKE.voteType))
                .userName(comment.getUser().getUserName())
                .build();


    }

    private Long countVote(Comment comment, Integer voteType) {
        List<Vote> byAttributeId = voteRepository.findByAttributeId(comment.getId());
        return byAttributeId.stream().filter(e -> voteType.equals(e.getVoteType())).count();
    }

}
