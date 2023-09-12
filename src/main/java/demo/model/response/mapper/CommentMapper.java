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
import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final VoteRepository voteRepository;
    public static final String SUCCESS = "Comment added successfully";


    public GetCommentResponse mapToResponse(Comment comment){
        List<Vote> voteList = findVote(comment);
        return  GetCommentResponse.builder()
                .data(comment.getMetadata())
                .commentId(comment.getId().toString())
                .userId(String.valueOf(comment.getUser().getId()))
                .postId(comment.getPost().getId().toString())
                .likeCount(countVote(voteList, VoteType.LIKE.voteType))
                .dislikeCount(countVote(voteList,VoteType.DISLIKE.voteType))
                .voteType(voteType(voteList,comment))
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



    private  List<Vote> findVote(Comment comment){
        return  voteRepository.findByAttributeId(comment.getId());
    }

    private int countVote(List<Vote> voteList, Integer voteType) {
        return (int)voteList.stream().filter(e -> voteType.equals(e.getVoteType())).count();
    }


    private int voteType(List<Vote> voteList, Comment comment) {
        Optional<Vote> vote = voteList.stream()
                .filter(e -> comment.getId().equals(e.getAttributeId()))
                .filter(a -> comment.getUser().getId().equals(a.getUser().getId()))
                .findFirst();
        return vote.map(Vote::getVoteType).orElse(0);
    }


}
