package demo.model.response.mapper;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        List<Vote> voteList = findVote(post);
        return  GetPostResponse.builder()
                .description(post.getMetaData())
                .postId(post.getId().toString())
                .userName(post.getUser().getUserName())
                .comment(getComments(comments))
                .commentCount(comments.size())
                .userId(String.valueOf(post.getUser().getId()))
                .likeCount(countVote(voteList, VoteType.LIKE.voteType))
                .dislikeCount(countVote(voteList, VoteType.DISLIKE.voteType))
                .voteType(voteType(voteList, post))
                .createdAt(post.getCreatedAt())
                .build();

    }
    public PostResponse mapToResponse(Post post){
        return PostResponse.builder()
                .message(SUCCESS)
                .postId(String.valueOf(post.getId()))
                .description(post.getMetaData())
                .createdAt(LocalDateTime.now())
                .build();
    }

    List<GetCommentResponse> getComments(List<Comment> comment) {
        return comment.stream()
                .map(commentMapper::mapToResponse).collect(Collectors.toList());
    }

    private  List<Vote> findVote(Post post){
       return  voteRepository.findByAttributeId(post.getId());
    }

    private int countVote(List<Vote> voteList, Integer voteType) {
        return (int)voteList.stream().filter(e -> voteType.equals(e.getVoteType())).count();
    }


    private int voteType(List<Vote> voteList, Post post) {
        Optional<Vote> vote = voteList.stream()
                .filter(e -> post.getId().equals(e.getAttributeId()))
                .filter(a -> post.getUser().getId().equals(a.getUser().getId()))
                .findFirst();
        return vote.map(Vote::getVoteType).orElse(0);
    }

}