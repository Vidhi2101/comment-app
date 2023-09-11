package demo.model.request;


import demo.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class AddVoteRequest {

    private String attributeId;
    private int voteType;
    private String userId;

    public Vote toVote(UUID attributeId, int voteType,User user){

        Vote vote = new Vote();
        vote.setVoteType(voteType);
        vote.setAttributeId(attributeId);
        vote.setUser(user);
        return vote;

    }
}
