package demo.requests;


import demo.entities.*;
import demo.repositories.CommentRepository;
import demo.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@AllArgsConstructor
@Data
public class AddVoteRequest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    private UUID attributeId;
    private String voteEntity;
    private int voteType;
    private UUID userId;

    public Vote toVote(UUID attributeId, int voteType,User user){
        Vote vote = new Vote();
        vote.setVoteType(voteType);
        vote.setAttributeId(attributeId);
        vote.setUser(user);
        return vote;

    }
}
