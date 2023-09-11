package demo.model.response.mapper;

import demo.AppConstants;
import demo.entities.Comment;
import demo.entities.Post;
import demo.entities.Vote;
import demo.entities.VoteType;
import demo.model.response.PostResponse;
import demo.repositories.VoteRepository;
import demo.model.response.GetCommentResponse;
import demo.model.response.GetPostResponse;
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

    public static final String SUCCESS = "Post created successfully";




    public GetPostResponse mapToResponse(Post post, List<Comment> comments){
        return  GetPostResponse.builder()
                .description(post.getMetaData())
                .postId(post.getId().toString())
                .userName(post.getUser().getUserName())
                .comment(getComments(comments))
                .commentCount(comments.size())
                .userId(String.valueOf(post.getUser().getId()))
                .likeCount(countVote(post, VoteType.LIKE.voteType))
                .dislikeCount(countVote(post, VoteType.DISLIKE.voteType))
                .createdAt(post.getCreatedAt())
                .build();

    }
    public PostResponse mapToResponse(Post post){
        return PostResponse.builder()
                .message(SUCCESS)
                .postId(String.valueOf(post.getId()))
                .description(post.getMetaData())
                .createdAt(post.getCreatedAt())
                .build();


    }

    List<GetCommentResponse> getComments(List<Comment> comment) {
        return comment.stream()
                .map(commentMapper::mapToResponse).collect(Collectors.toList());
    }

    private Long countVote(Post post, Integer voteType) {
        List<Vote> byAttributeId = voteRepository.findByAttributeId(post.getId());
        return byAttributeId.stream().filter(e -> voteType.equals(e.getVoteType())).count();
    }

}