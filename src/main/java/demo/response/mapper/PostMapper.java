package demo.response.mapper;

import demo.entities.Comment;
import demo.entities.Post;
import demo.entities.Vote;
import demo.entities.VoteType;
import demo.repositories.VoteRepository;
import demo.response.CommentResponse;
import demo.response.PostResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public  class PostMapper {

    @Autowired
    private  final  CommentMapper commentMapper;

    @Autowired
    private final VoteRepository voteRepository;



    public  PostResponse mapToResponse(Post post, List<Comment> comments){
        return  PostResponse.builder()
                .description(post.getMetaData())
                .id(post.getId().toString())
                .userName(post.getUser().getUserName())
                .comment(getComments(comments))
                .commentCount(comments.size())
                .likeCount(countVote(post, VoteType.LIKE.voteType))
                .dislikeCount(countVote(post, VoteType.DISLIKE.voteType))
                .build();

    }

    List<CommentResponse> getComments(List<Comment> comment) {
        return comment.stream()
                .map(commentMapper::mapToResponse).collect(Collectors.toList());
    }

    private Long countVote(Post post, Integer voteType) {
        List<Vote> byAttributeId = voteRepository.findByAttributeId(post.getId());
        return byAttributeId.stream().filter(e -> voteType.equals(e.getVoteType())).count();
    }

}