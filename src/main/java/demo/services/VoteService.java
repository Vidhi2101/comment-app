package demo.services;

import demo.entities.Vote;
import demo.model.request.AddVoteRequest;
import demo.model.response.VoteResponse;

public interface VoteService {

    Vote addVote(AddVoteRequest request) ;
    VoteResponse getUsers(String attributeId);

}
